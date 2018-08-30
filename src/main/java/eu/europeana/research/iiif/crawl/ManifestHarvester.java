package eu.europeana.research.iiif.crawl;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import java.util.Set;

import eu.europeana.research.iiif.discovery.syncdb.TimestampTracker;
import inescid.dataaggregation.store.Repository;
import inescid.util.HttpRequestException;
import inescid.util.HttpUtil;

public class ManifestHarvester {
private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
		.getLogger(ManifestHarvester.class);
	TimestampTracker manifestSource;
	String datasetUri;
	Repository repository;
	
	public ManifestHarvester(Repository repository, TimestampTracker manifestSource, String datasetUri) {
		super();
		this.manifestSource = manifestSource;
		this.datasetUri = datasetUri;
		this.repository = repository;
	}

	public void harvest() throws IOException, HttpRequestException, InterruptedException {
		Set<String> allDatasetManifests = repository.getAllDatasetResourceUris(datasetUri);
		
		int cnt=0;
		for(String mUrl: manifestSource.getIterableOfObjects(datasetUri, TimestampTracker.Deleted.EXCLUDE)) {
			cnt++;
			String manifestJson=null;
			try {
				List<org.apache.http.Header> headers = HttpUtil.getAndStoreWithHeaders(mUrl, repository.getFile(datasetUri, mUrl));
				repository.saveMeta(datasetUri, mUrl, HttpUtil.convertHeaderStruct(headers));
			} catch (IOException | HttpRequestException e) {
				log.error(e.getMessage(), e);
				continue;
			}
			repository.save(datasetUri, mUrl, manifestJson);
			allDatasetManifests.remove(mUrl);
			if(cnt%100 == 0)
				System.out.println(cnt+" manifests harvested");
		}

		Set<String> deletedManifests=allDatasetManifests;
		for(String mUrl: deletedManifests) {
			repository.remove(datasetUri, mUrl);
		}
	}




	public String httpGet(String url) throws IOException, HttpRequestException, InterruptedException {
		return HttpUtil.makeRequest(url).getContent().asString();
	}
}
