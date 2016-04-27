package vtx.util;


public class CommonUtil
{
	public static String getContentType(String name) {
        if (name == null) {
            return null;
        }
        
        int i = name.lastIndexOf(".");

        if (i < 0) {
            return null;
        }
        
        String extension = name.substring(i + 1).toLowerCase();

        switch (extension) {
            case "zip":
                return "application/zip";
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
           	 return "image/gif";
            case "mp3":
                return "audio/mpeg";
            case "m4a":
                return "audio/mp4";
            case "mov":
                return "video/quicktime";
            case "mp4":
                return "video/mp4";
            case "ogg":
                return "video/ogg";
            case "webm":
                return "video/webm";
            default:
                return null;
        }
   }
}
