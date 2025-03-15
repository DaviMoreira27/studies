file = main

compile: $(file)
	gcc -o $(file) $(file).c

run: $(file)
	./$(file)

$(file): $(file).c
	gcc -o $(file) $(file).c

.PHONY: run