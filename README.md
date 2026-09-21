# Projeto Sockets

Aplicação cliente-servidor via sockets TCP. O servidor, desenvolvido em Java,
atende até **3 clientes simultaneamente** e disponibiliza operações matemáticas
e o envio de uma imagem base64.

## Funcionalidades

- Divisão, adição e subtração de dois números;
- Solicitação da imagem disponível no servidor;
- Cliente em Java e cliente em Python;

## Requisitos

- Java (JDK) instalado, para compilar e executar o servidor e o cliente Java;
- Python 3 instalado, caso use o cliente Python.

## Como executar

Abra um terminal na raiz do projeto e entre na pasta `src`:

```powershell
cd src
```

### 1. Compilar os arquivos Java

```powershell
javac Servidor.java Conexao.java Cliente.java
```

### 2. Iniciar o servidor

Mantenha este terminal aberto. O servidor usa a porta `12345` e lê o arquivo
`imagem_servidor.png` do diretório atual.

```powershell
java Servidor
```

### 3. Iniciar um cliente

Em outro terminal, também dentro de `src`, escolha uma das opções abaixo.

Cliente Java:

```powershell
java Cliente
```

Cliente Python:

```powershell
python Cliente.py
```

## Menu do cliente

| Opção | Ação |
| --- | --- |
| `1` | Divide o primeiro número pelo segundo |
| `2` | Soma dois números |
| `3` | Subtrai o segundo número do primeiro |
| `4` | Baixa a imagem do servidor |
| `0` | Encerra a conexão |

Os números podem ser informados com vírgula ou ponto decimal. Ao escolher a
opção `4`, a imagem recebida é salva como `imagem_recebida.png` dentro de `src`.

## Estrutura

```text
src/
├── Servidor.java          # Inicializa o servidor TCP
├── Conexao.java           # Processa cada conexão de cliente
├── Cliente.java           # Cliente Java interativo
├── Cliente.py             # Cliente Python interativo
└── imagem_servidor.png    # Imagem enviada pelo servidor
```

