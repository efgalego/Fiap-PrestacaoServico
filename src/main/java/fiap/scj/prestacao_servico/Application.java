package fiap.scj.prestacao_servico;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import fiap.scj.prestacao_servico.repositorio.CidadaoRepositorio;
import fiap.scj.prestacao_servico.to.CidadaoTO;

public class Application {

	private static final String RESPONSE = ".RESPONSE";
	public static Path contextPath;
	
	public static void main(String[] args) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			String dirManaged = "C:/PrestacaoServico/";
			if (args.length > 1) {
				dirManaged = args[1];
			}
			contextPath = Paths.get(dirManaged);
			contextPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

			System.out.println("Sistema de Prestacao de Servicos registrado para diretorio: " + contextPath.getFileName());

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

				try {
					for (WatchEvent<?> event : key.pollEvents()) {
						WatchEvent.Kind<?> kind = event.kind();

						@SuppressWarnings("unchecked")
						WatchEvent<Path> ev = (WatchEvent<Path>) event;
						Path fileName = ev.context();

						System.out.println("Novo evento [" + kind.name() + "]: " + fileName);

						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}
						if (kind == StandardWatchEventKinds.ENTRY_CREATE && !fileName.toString().endsWith(RESPONSE)) {
							
							CidadaoTO to = processarArquivo(fileName);							
							if (to != null) {
								String registro = new CidadaoRepositorio().adicionar(to);
								criarArquivoResposta(registro, fileName);
							}
						}
					}
				} finally {
					key.reset();
				}
			}
		} catch (IOException e) {
			System.err.println("Erro ao gerenciar pasta: " + e.toString());
		}
	}

	private static CidadaoTO processarArquivo(Path fileName) {
		try {
			String conteudo = new String(Files.readAllBytes(Paths.get(contextPath.toString() + File.separatorChar + fileName.toString())));
			
			QNameMap qmap = new QNameMap();
			qmap.setDefaultNamespace("http://www.fiap.com.br");
			qmap.setDefaultPrefix("ns0");
			StaxDriver staxDriver = new StaxDriver(qmap); 
			XStream xstream = new XStream(staxDriver);
			xstream.alias("CidadaoTO", CidadaoTO.class);
			
			CidadaoTO to = (CidadaoTO) xstream.fromXML(conteudo);
			System.out.println(to);
			return to;
		} catch (Exception e) {
			System.err.println("Erro ao processar entrada: " + e.toString());
			return null;
		}
	}
	
	private static void criarArquivoResposta(String registro, Path originalFileName) {
		try {
			String novoFile = contextPath.toString() + File.separatorChar + originalFileName.toString() + RESPONSE;
			Files.write(Paths.get(novoFile), registro.getBytes());
		} catch (IOException e) {
			System.err.println("Erro ao gravar resposta: " + e.toString());
		}
	}

}
