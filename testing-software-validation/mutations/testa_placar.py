import os

# Lucas Michael Genovese Huss Oliveira - 15577610
# Davi Moreira de Santana - 15447584
# Larissa de Melo Andrade - 15415640

# Cria a sessão de teste
os.system("python -m pyproteum testnew --D . --S placar.py placar_session")

# Adiciona os testes de funcionalidade
os.system("python -m pyproteum tcase --add --D . --S funcional.py placar_session")

# Adiciona os testes MCDC
os.system("python -m pyproteum tcase --add --D . --S mcdc.py placar_session")

# Gera todos os mutantes
os.system("python -m pyproteum mutagen --create --all 100 0 --D . placar_session")

# Adiciona os novos testes
os.system("python -m pyproteum tcase --add --D . --S mutation.py placar_session")

# Executa os testes contra os mutantes
os.system("python -m pyproteum exemuta --exec placar_session")
