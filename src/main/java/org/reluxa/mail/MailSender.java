package org.reluxa.mail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.reluxa.settings.Config;
import org.reluxa.settings.service.SettingsServiceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MailSender implements MailSenderIF {
	
  protected Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Inject
	private SettingsServiceIF settings;
	

	private static HttpURLConnectionFactory factory = new HttpURLConnectionFactory() {
		@Override
		public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
			Proxy wp = null;
			System.setProperty("java.net.useSystemProxies", "true");
			try {
				List<Proxy> l = ProxySelector.getDefault().select(new URI("https://api.mailgun.net/"));
				for (Proxy proxy : l) {
					wp = proxy;
				}
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (HttpURLConnection) url.openConnection(wp);
		}
	};

	@Override
	public boolean sendMail(Collection<String> recipients, String subject, String body, String htmlText) {
		URLConnectionClientHandler ch = new URLConnectionClientHandler(factory);
		Client client = new Client(ch);
		client.addFilter(new HTTPBasicAuthFilter("api", "key-1vxsx9uh6z185hq4ny0frl8iednc1si8"));
		WebResource webResource = client.resource("https://api.mailgun.net/v2/squash.reluxa.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", settings.getConfig().getSenderEmailAddress());
		formData.add("to", settings.getConfig().getSenderEmailAddress());
		for (String recipient : recipients) {
			formData.add("bcc", getEmail(recipient));
		}
		formData.add("subject", subject);
		formData.add("html", htmlText);
		formData.add("text", body);
		log.debug("Sending message: "+formData.toString());
		ClientResponse resp = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
		log.debug("Result: ", resp.toString());
		return Status.OK.getStatusCode() == resp.getStatusInfo().getStatusCode();
	}
	
	private String getEmail(String email) {
		Config config = settings.getConfig();
		if (StringUtils.isNotEmpty(config.getRedirectEmailAddress())) {
			return config.getRedirectEmailAddress();
		}
		return email;
	}

}
