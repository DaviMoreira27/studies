SRC = Register.java Index.java RegisterManager.java Main.java
PKG = exec07
MAIN_CLASS = $(PKG).Main

all:
	javac --release 17 -d . $(SRC)

run: all
	java $(MAIN_CLASS)

run-silent:
	@javac --release 17 -d . $(SRC)
	@java $(MAIN_CLASS)

clean:
	rm -f $(PKG)/*.class registers.txt temp.txt

test:
	@echo "=== Executando testes ==="
	@echo "=== RELATÓRIO DE TESTES ===" > test_results.txt
	@echo "Data: $$(date)" >> test_results.txt
	@echo "" >> test_results.txt
	@passed=0; failed=0; \
	for input in tests_exec07/*.in; do \
		if [ -f "$$input" ]; then \
			test_num=$$(basename "$$input" .in); \
			expected="tests_exec07/$$test_num.out"; \
			if [ -f "$$expected" ]; then \
				echo -n "Teste $$test_num: "; \
				echo "================== TESTE $$test_num ==================" >> test_results.txt; \
				echo "Arquivo de entrada: $$input" >> test_results.txt; \
				echo "" >> test_results.txt; \
				echo "--- ENTRADA ---" >> test_results.txt; \
				cat "$$input" >> test_results.txt; \
				echo "" >> test_results.txt; \
				echo "--- SAÍDA OBTIDA ---" >> test_results.txt; \
				$(MAKE) --no-print-directory run-silent < "$$input" 2>/dev/null | tee temp_output.txt >> test_results.txt; \
				echo "" >> test_results.txt; \
				echo "--- SAÍDA ESPERADA ---" >> test_results.txt; \
				cat "$$expected" >> test_results.txt; \
				echo "" >> test_results.txt; \
				if diff -q temp_output.txt "$$expected" >/dev/null 2>&1; then \
					echo "PASSOU"; \
					echo "RESULTADO: PASSOU ✓" >> test_results.txt; \
					passed=$$((passed + 1)); \
				else \
					echo "FALHOU"; \
					echo "RESULTADO: FALHOU ✗" >> test_results.txt; \
					echo "" >> test_results.txt; \
					echo "--- DIFERENÇAS ---" >> test_results.txt; \
					diff temp_output.txt "$$expected" >> test_results.txt 2>/dev/null || echo "Arquivos diferem" >> test_results.txt; \
					failed=$$((failed + 1)); \
				fi; \
				echo "" >> test_results.txt; \
				echo "" >> test_results.txt; \
				rm -f temp_output.txt; \
			fi; \
		fi; \
	done; \
	echo "=== Resumo: $$passed passou, $$failed falhou ==="; \
	echo "=== RESUMO FINAL ===" >> test_results.txt; \
	echo "Testes executados: $$((passed + failed))" >> test_results.txt; \
	echo "Passou: $$passed" >> test_results.txt; \
	echo "Falhou: $$failed" >> test_results.txt; \
	echo ""; \
	echo "Relatório detalhado salvo em: test_results.txt"

.PHONY: test