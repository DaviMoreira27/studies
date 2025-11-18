Java 17


coverage run --branch funcional.py
davisantana@fedora:~/studies/testing-software-validation/score-functional$ coverage erase
davisantana@fedora:~/studies/testing-software-validation/score-functional$ coverage run --branch -m unittest funcional.py
.......................
----------------------------------------------------------------------
Ran 23 tests in 0.003s

OK
davisantana@fedora:~/studies/testing-software-validation/score-functional$ coverage html
Wrote HTML report to htmlcov/index.html
davisantana@fedora:~/studies/testing-software-validation/score-functional$
