import math
import sys
from pyscipopt import Model

# Setando as demandas das vigas por periodo, dois tipos de vigas, para tres periodos.
demands = {
    1: [100.0, 90.0, 120.0],
    2: [40.0, 50.0, 80.0],
}

# Uma unidade do item 1 consome 15 minutos de 60. Produzir uma unidade do item 2 demora 20 minutos de 60
prod_time_hours = {1: 15.0/60.0, 2: 20.0/60.0}
prod_cost = {
    1: [20.0, 20.0, 30.0],
    2: [20.0, 20.0, 30.0],
}
holding_cost = {
    1: [2.0, 3.0],
    2: [2.5, 3.5],
}

# Tamanho da hora regular
regular_hours = 40.0

periods = [1, 2, 3]
items = [1, 2]

overtime_hour_cost = sys.stdin.read().strip().split()
if len(overtime_hour_cost) >= 1:
    X = float(overtime_hour_cost[0])
else:
    X = 0.0

model = Model("Lotsizing")

model.setParam("display/verblevel", 0)

q = {(i, t): model.addVar(vtype="CONTINUOUS", lb=0, name=f"q_{i}_{t}") for i in items for t in periods} # quantidade produzida do item i no período t
s = {(i, t): model.addVar(vtype="CONTINUOUS", lb=0, name=f"s_{i}_{t}") for i in items for t in periods} # estoque do item i ao final do período t
h = {t: model.addVar(vtype="CONTINUOUS", lb=0, name=f"h_{t}") for t in periods} # número de horas extras usadas no período t

for i in items:
    for t in periods:
        if t == 1:
            # para o periodo 1, o estoque final = produção - demanda
            model.addCons(s[i, t] == q[i, t] - demands[i][t-1])
        else:
            # para os outros, estoque final = estoque anterior + produção - demanda
            model.addCons(s[i, t] == s[i, t-1] + q[i, t] - demands[i][t-1])

# restricao de capacidade de fabrica
#
# o tempo gasto produzindo algo, deve ser menor que o tempo das horas regulares + horas extras
for t in periods:
    model.addCons(
        sum(prod_time_hours[i] * q[i, t] for i in items) <= regular_hours + h[t]
    )

production_cost = sum(prod_cost[i][t-1] * q[i, t] for i in items for t in periods) # custo unitário * quantidade produzida
holding_costs = sum(holding_cost[i][t-1] * s[i, t] for i in items for t in periods if t < len(periods)) # custo de manter estoque até período 2
overtime_costs = X * sum(h[t] for t in periods)

for i in items:
    model.addCons(s[i, periods[-1]] == 0) # impoem que o estoque deve estar zerado ao final do ultimo periodo,
    # na pratica nao muda nada ja que o estoque no fim do periodo 3 nao e levado em consideracao no final

model.setObjective(production_cost + holding_costs + overtime_costs, "minimize") # o nosso objetivo e minimizar custo total de produção + estoque + horas extras.

model.optimize()

total_cost = model.getObjVal()
print(int(math.floor(total_cost)))
