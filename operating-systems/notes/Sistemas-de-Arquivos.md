- Informações armazenadas em arquivos devem ser persistentes
- SO deve saber aonde os arquivos estão armazenados fisicamente e como os arquivos são referenciados (links, hard e soft).

## Como os arquivos podem ser estruturados

### Sequência não estruturada de bytes

- Arquivos são apenas conjuntos de bytes
- O significado é atribuido a nível do usuário (aplicativos)
- **Vantagem**: Flexibilidade, os usuários nomeiam seus arquivos como quiserem.

### Sequência de registros de tamanhos fixos, cada qual com sua estrutura interna

- Leitura/escrita são realizadas em registros
- Não utilizado

### Árvores de registros (tamanho variado), cada qual com um campo chave em uma posição fixa:

- Mainframes
- SO decide onde colocar o arquivo.

## Tipos de arquivos

### Regulares

- Contém Informações do usuário

#### Regulares - ASCII

- Linhas de texto
- Facilitam integração de Arquivos

#### Regulares - Binários

- Todo arquivo não **ASCII**, possuem uma estrutura interna conhecida pelos aplicativos que os usam
- Pode ser um programa executável

### Diretórios

- São arquivos responsáveis por manter a estrutura do sistema de arquivos

### Arquivos Especiais de Caracteres

- Aqueles relacionados com E/S e utilizados para modelar dispositivos seriais de E/S

### Arquivos especiais de bloco

- São aqueles utilizados para modelar discos

**Inodes Linux**
