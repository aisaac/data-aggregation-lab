package inescid.dataaggregation.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.plaf.FileChooserUI;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.jena.ext.com.google.common.math.LinearTransformation;

import com.mchange.v1.util.SimpleMapEntry;

import inescid.dataaggregation.dataset.Dataset;
import inescid.dataaggregation.dataset.GlobalCore;

public class Repository {
	private static final Charset UTF8 = Charset.forName("UTF-8");

	static private class FilenameManager {
		public static String getDatasetFolderName(String datasetUri) {
			try {
				return URLEncoder.encode(datasetUri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				/* impossible */ return null;
			}
		}

		public static String getResourceFolderAndFilename(String resourcetUri) {
			try {
				String filename = URLEncoder.encode(resourcetUri, "UTF-8");
				String folder = String.valueOf(Math.abs(filename.hashCode() % 1000));
				return folder + "/" + filename;
			} catch (UnsupportedEncodingException e) {
				/* impossible */ return null;
			}
		}

		public static String getMetaResourceFolderAndFilename(String resourcetUri) {
			try {
				String uriUrlEncoded = URLEncoder.encode(resourcetUri, "UTF-8");
				String filename = uriUrlEncoded + ".meta.csv";
				String folder = String.valueOf(Math.abs(uriUrlEncoded.hashCode() % 1000));
				return folder + "/" + filename;
			} catch (UnsupportedEncodingException e) {
				/* impossible */ return null;
			}
		}

		public static String getResourcetUri(File resourceFile) {
			try {
				return URLDecoder.decode(resourceFile.getName(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				/* impossible */ return null;
			}
		}
	}

	File homeFolder = new File("target");
	// File tmpFolder=new File(homeFolder, "tmp");

	public void init(String homeFolderPath) {
		homeFolder = new File(homeFolderPath);
		if (!homeFolder.exists())
			homeFolder.mkdirs();
		// tmpFolder=new File(homeFolder, "tmp");
		// if (!tmpFolder.exists())
		// tmpFolder.mkdirs();
	}

	public File getDatasetLogFile(String datasetUri) {
		return new File(homeFolder, FilenameManager.getDatasetFolderName(datasetUri) + "/dataset_log.txt");
	}

	public File getDatasetFolder(String datasetUri) {
		return new File(homeFolder, FilenameManager.getDatasetFolderName(datasetUri));
	}

	public File getFile(String datasetUri, String resourceUri) {
		return new File(getDatasetFolder(datasetUri), FilenameManager.getResourceFolderAndFilename(resourceUri));
	}

	public File getMetaFile(String datasetUri, String resourceUri) {
		return new File(getDatasetFolder(datasetUri), FilenameManager.getMetaResourceFolderAndFilename(resourceUri));
	}
	public List<Entry<String, String>> getMeta(String datasetUri, String resourceUri) throws IOException {
		List<Entry<String, String>> meta=new ArrayList<>();
		File metaFile = getMetaFile(datasetUri, resourceUri);
		if(metaFile.exists()) {
			CSVParser parser=CSVParser.parse(metaFile, UTF8, CSVFormat.DEFAULT);
			parser.forEach(entry -> {
				meta.add(new AbstractMap.SimpleEntry<String, String>(entry.get(0),entry.get(1)));
			});
			parser.close();
		}
		return meta;
	}
	public List<Entry<String, File>> getAllDatasetResourceFiles(String datasetUri) {
		final List<Entry<String, File>> files = new ArrayList<>();
		File datasetFolder = getDatasetFolder(datasetUri);
		if (datasetFolder.listFiles() != null)
			for (File folder : datasetFolder.listFiles()) {
				if (!folder.isDirectory() || folder.getName().equals("seealso"))
					continue;
				for (File resourceFile : folder.listFiles()) {
					if (!resourceFile.isFile() || resourceFile.getName().endsWith(".meta.csv"))
						continue;
					files.add(new AbstractMap.SimpleEntry<String, File>(FilenameManager.getResourcetUri(resourceFile),
							resourceFile));
				}
			}
		return files;
	}

	private List<Entry<String, File>> getAllDatasetMetaFiles(String datasetUri) {
		final List<Entry<String, File>> files = new ArrayList<>();
		File datasetFolder = getDatasetFolder(datasetUri);
		if (datasetFolder.listFiles() != null)
			for (File folder : datasetFolder.listFiles()) {
				if (!folder.isDirectory() || folder.getName().equals("seealso"))
					continue;
				for (File resourceFile : folder.listFiles()) {
					if (!resourceFile.isFile() || !resourceFile.getName().endsWith(".meta.csv"))
						continue;
					files.add(new AbstractMap.SimpleEntry<String, File>(FilenameManager.getResourcetUri(resourceFile),
							resourceFile));
				}
			}
		return files;
	}

	public void save(String datasetUri, String resourceUri, String content) throws IOException {
		File manifestFile = getFile(datasetUri, resourceUri);
		FileUtils.write(manifestFile, content, UTF8);
	}

	public void save(String datasetUri, String resourceUri, String content, List<Entry<String, String>> meta)
			throws IOException {
		save(datasetUri, resourceUri, content);
		saveMeta(datasetUri, resourceUri, meta);
	}

	
	
	public void saveMeta(String datasetUri, String resourceUri, List<Entry<String, String>> meta) throws IOException {
		File metaFile = getMetaFile(datasetUri, resourceUri);
		FileWriterWithEncoding fileWriter = new FileWriterWithEncoding(metaFile, GlobalCore.UTF8);
		CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
		for (Entry<String, String> field : meta) {
			printer.printRecord(field.getKey(), field.getValue());
		}
		printer.close();
		fileWriter.close();
	}

	public void save(String datasetUri, String resourceUri, byte[] content) throws IOException {
		File manifestFile = getFile(datasetUri, resourceUri);
		FileUtils.writeByteArrayToFile(manifestFile, content);
	}

	public void save(String datasetUri, String resourceUri, byte[] content, List<Entry<String, String>> meta)
			throws IOException {
		save(datasetUri, resourceUri, content);
		saveMeta(datasetUri, resourceUri, meta);
	}

	public Set<String> getAllDatasetResourceUris(String datasetUri) {
		final Set<String> uris = new HashSet<>();
		File datasetFolder = getDatasetFolder(datasetUri);
		if (datasetFolder.exists())
			for (File folder : datasetFolder.listFiles()) {
				if (!folder.isDirectory() || folder.getName().equals("seealso"))
					continue;
				for (File intermediateFolder : folder.listFiles()) {
					if (!intermediateFolder.isDirectory())
						continue;
					for (File resourceFile : intermediateFolder.listFiles()) {
						if (!resourceFile.isFile())
							continue;
						if (!resourceFile.getName().endsWith(".meta.xml"))
							uris.add(FilenameManager.getResourcetUri(resourceFile));
					}
				}
			}
		return uris;
	}

	public void remove(String datasetUri, String resourceUri) {
		File manifestFile = getFile(datasetUri, resourceUri);
		manifestFile.delete();
		File metaFile = getMetaFile(datasetUri, resourceUri);
		if (metaFile.exists())
			metaFile.delete();
		if (manifestFile.getParentFile().list().length == 0)
			manifestFile.getParentFile().delete();
	}

	public void clear(Dataset dataset) {
		File logFile = getDatasetLogFile(dataset.getUri());
		if (logFile.exists())
			logFile.delete();
		List<Entry<String, File>> files = getAllDatasetMetaFiles(dataset.getUri());
		for (Entry<String, File> manifEntry : files) {
			if (manifEntry.getValue().exists())
				manifEntry.getValue().delete();
		}
		files = getAllDatasetResourceFiles(dataset.getUri());
		for (Entry<String, File> manifEntry : files) {
			if (manifEntry.getValue().exists())
				manifEntry.getValue().delete();
		}
		for (Entry<String, File> manifEntry : files) {
			if (manifEntry.getValue().getParentFile().exists())
				manifEntry.getValue().getParentFile().delete();
		}
	}

	public Iterable<String> getIteratorOfAllDatasetResourceUris(String datasetUri) {
		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					String next = null;
					int idx1 = -1;
					int idx2 = 0;
					File[] col1;
					File[] col2;
					{
						File datasetFolder = getDatasetFolder(datasetUri);
						if (datasetFolder.exists()) {
							col1 = datasetFolder.listFiles();
							prepareNext();
						}
					}

					@Override
					public boolean hasNext() {
						return next != null;
					}

					@Override
					public String next() {
						String ret = next;
						prepareNext();
						return ret;
					}

					private void prepareNext() {
						next = null;
						OUT: while (next == null && idx1 < col1.length - 1) {
							idx1++;
							idx2 = 0;
							while (true) {
								if (!col1[idx1].isDirectory() || col1[idx1].getName().equals("seealso"))
									idx1++;
								else
									break;
								if (idx1 >= col1.length)
									break OUT;
							}
							col2 = col1[idx1].listFiles();
							while (idx2 <= col2.length) {
								if (!col2[idx2].isFile())
									continue;
								if (!col2[idx2].getName().endsWith(".meta.xml")) {
									next = FilenameManager.getResourcetUri(col2[idx2]);
									break OUT;
								}
							}
						}
					}
				};
			}
		};
	}
}