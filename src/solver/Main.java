package solver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Insira o caminho:");
		String path = sc.nextLine();
		System.out.println("Insira o campo a ser alterado (em número contando da esquerda pra direita):");
		String campo= sc.nextLine();
		System.out.println("Insira a sequencia correta deste campo:");
		String sequenciaCorreta = sc.nextLine();
		sc.close();
//		String path = "C:\\Users\\zoeir\\Downloads\\Arquivos\\MovimentacaoBem.txt";
//		String campo = String.valueOf(2);
//		String sequenciaCorreta = String.valueOf(170);
		
		try(BufferedReader bf = new BufferedReader(new FileReader(path))){
			
			String line = bf.readLine();
			
			while(line != null) {
			String linhaCorreta = "";
			String[] splitLine = line.split("\\|");
			splitLine[Integer.valueOf(campo) - 1] = sequenciaCorreta;
			
			for(int i = 0; i <splitLine.length; i++) {
				splitLine[i] += "|";
			}
			for (String t : splitLine) {
				linhaCorreta+= t;
			}
			System.out.println(linhaCorreta);
			
			sequenciaCorreta = String.valueOf(Integer.valueOf(sequenciaCorreta) + 1);
	
			line = bf.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error:" + e.getMessage());
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-");
			System.out.println("Seu arquivo possui linhas vazias, verifique!");
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-");
		}
		

	}

}
