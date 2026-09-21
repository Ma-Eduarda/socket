import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Conexao {

    Socket socket;
    ArrayBlockingQueue<String> fila = new ArrayBlockingQueue<>(50);

    public Conexao(Socket socket) {
        this.socket = socket;
    }

    public void iniciar() {

        Thread ler = new Thread(() -> {
            try {
                Scanner entrada = new Scanner(socket.getInputStream());
                while (entrada.hasNextLine()) {
                    String mensagem = entrada.nextLine();
                    System.out.println("Mensagem recebida: " + mensagem);
                    if (mensagem.equals("0")) {
                        break;
                    }
                    fila.put(processar(mensagem));
                }
                fila.put("CONEXAO ENCERRADA!");
            } catch (IOException | InterruptedException e) {
                fila.offer("CONEXAO ENCERRADA!");
            }
        });

        Thread escrever = new Thread(() -> {
            try {
                PrintStream saida = new PrintStream(socket.getOutputStream(), true);
                String mensagem;
                do {
                    mensagem = fila.take();
                    if (mensagem.equals("CONEXAO ENCERRADA!")) {
                        Servidor.conexoes.remove(this);
                    }
                    saida.println(mensagem);
                } while (!mensagem.equals("CONEXAO ENCERRADA!"));
            } catch (IOException | InterruptedException e) {
                System.out.println("Erro na escrita: " + e.getMessage());
            } finally {
                fechar();
            }
        });

        ler.start();
        escrever.start();
    }

    void fechar() {
        try {
            socket.close();
        } catch (IOException e) {
        }
        Servidor.conexoes.remove(this);

        System.out.println();
        System.out.println(
                "Cliente "
                + socket.getInetAddress().getHostAddress()
                + ":"
                + socket.getPort()
                + " saiu."
        );
        System.out.println(
                "Clientes conectados: "
                + Servidor.conexoes.size()
                + "/"
                + Servidor.maxClientes
        );
    }

    String processar(String mensagem) {
        try {
            String[] partes = mensagem.trim().split("\\s+");
            String opcao = partes[0];

            if (opcao.equals("1")
                    || opcao.equals("2")
                    || opcao.equals("3")) {

                if (partes.length != 3) {
                    return "ERRO: mensagem invalida";
                }

                double numero1 = Double.parseDouble(partes[1]);
                double numero2 = Double.parseDouble(partes[2]);

                if (opcao.equals("1")) {
                    if (numero2 == 0) {
                        return "ERRO: divisao por zero";
                    }
                    return String.valueOf(numero1 / numero2);
                }

                if (opcao.equals("2")) {
                    return String.valueOf(numero1 + numero2);
                }

                return String.valueOf(numero1 - numero2);
            }

            // Imagem
            if (opcao.equals("4")) {
                System.out.println("Cliente pediu a imagem.");
                return Servidor.imagemBase64;
            }
            return "ERRO: opcao invalida";

        } catch (NumberFormatException e) {
            return "ERRO: mensagem invalida";
        }
    }
}
