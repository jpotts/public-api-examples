import org.alfresco.cmis.client.AlfrescoDocument;
import org.alfresco.cmis.client.AlfrescoFolder;
import org.apache.chemistry.opencmis.client.api.Folder
import org.apache.chemistry.opencmis.client.api.Session
import org.apache.chemistry.opencmis.commons.enums.VersioningState
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl
import org.springframework.social.alfresco.api.Alfresco
import org.springframework.social.alfresco.api.entities.Network
import org.springframework.social.alfresco.api.entities.Person

import com.myproject.*
import com.myproject.servlets.*

AuthorizedApiConnection api_con = session[DanceStart.ALFRESCO_API_CONNECTION]

	Alfresco alfresco = api_con.connection
	Network network = api_con.network
	Person person = api_con.person
	Session cmisSession = api_con.cmisSession
	
	AlfrescoFolder documentLibrary = (AlfrescoFolder)cmisSession.getObjectByPath("/Sites/public-api-trial-site/documentlibrary")

	Map props = ["cmis:objectTypeId":"cmis:folder","cmis:name":"photos1"]
	AlfrescoFolder photosFolder = (AlfrescoFolder)documentLibrary.createFolder(props)

	def photo = new File("/users/gjames/Downloads/Photo1.jpg")
	Map props = ["cmis:objectTypeId":"cmis:document","cmis:name":"gethin's photo"]
	ContentStreamImpl fileContent = new ContentStreamImpl(mimeType: "image/jpeg", stream:photo.newInputStream())

	AlfrescoDocument document = (AlfrescoDocument)photosFolder.createDocument(props, fileContent, VersioningState.MAJOR)
	alfresco.addTagToNode(network.getId(), document.getId(), "devcon2012");
	
	

html.html {
    head {
        title 'Do great work'
    }
    body {
        h1 'Do great work'

		if (api_con)	 {
			section "Photos folder id is ${photosFolder.id}"
			section "Get a photo? ${photo.isFile()}"
			section "Photo uploaded as ${d.id}"
		}

    }
}