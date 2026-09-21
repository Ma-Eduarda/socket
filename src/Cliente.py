import base64
import socket

sock = socket.create_connection(("127.0.0.1", 12345))
entrada = sock.makefile("r")
saida = sock.makefile("w")

print("Conectado ao servidor.")
print()

while True:
    print("Menu:")
    print("1 - Divisao")
    print("2 - Adicao")
    print("3 - Subtracao")
    print("4 - Pedir imagem ao servidor")
    print("0 - Encerrar")
    print()
    opcao = input("Opcao: ")

    if opcao in ("1", "2", "3"):
        numero1 = input("Digite o primeiro numero: ").strip().replace(",", ".")
        numero2 = input("Digite o segundo numero: ").strip().replace(",", ".")

        mensagem = opcao + " " + numero1 + " " + numero2
        
    elif opcao == "4": mensagem = "4"

    elif opcao == "0": mensagem = "0"

    else:
        print("Opcao invalida.")
        print()
        continue

    saida.write(mensagem + "\n")
    saida.flush()

    resposta = entrada.readline().strip()
    if resposta == "":
        print("Servidor encerrou a conexao.")
        break

    if opcao == "4" and not resposta.startswith("ERRO"):
        with open("imagem_recebida.png", "wb") as arquivo:
            arquivo.write(base64.b64decode(resposta))
        print("Imagem recebida e salva como imagem_recebida.png")
        print()
        
    else:
        print("Resultado:", resposta)
        print()

    if opcao == "0":
        break

    if resposta == "ERRO: servidor cheio":
        break

sock.close()

print("Cliente encerrado.")