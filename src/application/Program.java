package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		//String path = "C:\\Temp\\in.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			List<Sale> listSale = new ArrayList<>();

			while (line != null) {
				String[] fields = line.split(",");
				listSale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");

			Comparator<Sale> comp = (x, y) -> x.averagePrice().compareTo(y.averagePrice());
			List<Sale> vendas = listSale.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());

			vendas.forEach(System.out::println);
			
			System.out.println();
			
			Double totalVendido = listSale.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
					
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", totalVendido));
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		sc.close();
	}

}

//base de dados https://gist.githubusercontent.com/acenelio/e4e169691ee5aef2c56c87bc22a54379/raw/68810e53937ea5d7a119dd935440edc4365bb36f/base-de-dados.csv