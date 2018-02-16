## gcpimageserver
hybris-gcpmediaserver is a hybris extension to integrate media management with Google Cloud Platform's (GCP) cloud storage. The integration is seamless and can be configured on a folder-by-folder basis. The media can be created and can be deleted in the backoffice and the changes will reflect in the GCP's cloud storage.

### pre-requisites
* a [google cloud platform](https://cloud.google.com/) account
* an [existing bucket](https://cloud.google.com/storage/docs/quickstart-console) configured in cloud storage
* an environment variable "GOOGLE_APPLICATION_CREDENTIALS" was already set pointing to the [JSON file](https://cloud.google.com/storage/docs/reference/libraries) of the [service account key](https://cloud.google.com/storage/docs/reference/libraries)

### hybris integration
1. place gcpimageserver inside the custom folder and add `<extension name='gcpimageserver' />` in your localextensions.xml file.
2. configure your media folder in hybris for example
3. configure your local.properties to use the extension. below is a sampel configuration of media folder "google" to store the media in GCP Cloud Storage
~~~~
media.folder.google.storage.strategy=gcpMediaStorageStrategy
media.folder.google.url.strategy=gcpMediaWebURLStrategy
gcp.folder.google.bucket.name=codingzombies-gcp
~~~~
4. you're now ready to use GCP Cloud Storage!

## Screenshots
### BackOffice
![BackOffice](https://raw.githubusercontent.com/codezombies/gcpimageserver/master/resources/images/backoffice.png)

### Homepage
![Homepage](https://raw.githubusercontent.com/codezombies/gcpimageserver/master/resources/images/homepage.png)

