1. python -m pyproteum testnew --D . --S placar.py placar_session
2. python -m pyproteum exemuta --exec placar_session
3. python -m pyproteum tcase --add --D . --S funcional.py placar_session
4. python -m pyproteum tcase --add --D . --S mcdc.py placar_session
5. python -m pyproteum mutagen --create --all 100 0 --D . placar_session
6. python -m pyproteum exemuta --exec placar_session
7. python -m pyproteum mutaview --gui placar_session
8. python -m pyproteum tcase --add --D . --S mutation.py placar_session
