import math
import sys
import gurobipy as gp
from gurobipy import GRB

demands = {
    1: [100.0, 90.0, 12.0],
    2: [40.0, 50.0, 80.0],
}

prod_time_hours = {1: 15.0/60.0, 2: 20.0/60.0}
prod_cost = {
    1: [20.0, 20.0, 30.0],
    2: [20.0, 20.0, 30.0],
}
holding_cost = {
    1: [2.0, 3.0],
    2: [2.5, 3.5],
}
regular_hours = 40.0
periods = [1, 2, 3]
items = [1, 2]

data = sys.stdin.read().strip().split()

if len(data) >= 1:
    X = float(data[0])
else:
    X = 10.0

model = gp.Model("Lotsizing")
model.setParam("OutputFlag", 0)

# Variáveis
q = model.addVars(items, periods, lb=0, name="q")  # produção
s = model.addVars(items, periods, lb=0, name="s")  # estoque
h = model.addVars(periods, lb=0, name="h")         # horas extras

for i in items:
    for t in periods:
        if t == 1:
            model.addConstr(s[i, t] == q[i, t] - demands[i][t-1])
        else:
            model.addConstr(s[i, t] == s[i, t-1] + q[i, t] - demands[i][t-1])

for t in periods:
    model.addConstr(
        gp.quicksum(prod_time_hours[i] * q[i, t] for i in items)
        <= regular_hours + h[t]
    )

production_cost = gp.quicksum(prod_cost[i][t-1] * q[i, t] for i in items for t in periods)
holding_costs = gp.quicksum(holding_cost[i][t-1] * s[i, t] for i in items for t in [1, 2])
overtime_costs = X * gp.quicksum(h[t] for t in periods)

model.setObjective(production_cost + holding_costs + overtime_costs, GRB.MINIMIZE)

model.optimize()

total_cost = model.objVal
print(int(math.floor(total_cost)))
