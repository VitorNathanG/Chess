# CHESSBRAIN
### Programa simples de xadrez que roda no terminal.

### Guia básico de Xadrez do [chess.com](https://www.chess.com/lessons/how-to-move-the-pieces)

## Exemplo de movimentação das peças:

> e2 e4 -> move a peça em e2 para e4

## Opções

**-new** inicia um novo jogo

**-end** encerra a aplicação

__*Atenção*__: a aplicação é case sensitive

Disponível como um container do Docker. Para executar o container:

> docker run -i vitorgoncalves/chessbrain

## Criando o container a partir do fonte
1. Clonar o repositório: `git clone https://github.com/VitorNathanG/Chess.git` 

1. Entrar no repositório `cd Chess`

1. Mudar para branch docker `git checkout docker`

1. Faça a construção do container com o comando `docker build -t chessbrain .`

1. Execute o container usando `docker run -i chessbrain`

### A aplicação está em estágio embrionário de desenvolvimento e pode apresentar erros muito graves. Use por sua conta e risco.
