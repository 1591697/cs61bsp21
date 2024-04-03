package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static gitlet.Repository.OBJECTS_DIR;
import static gitlet.Repository.STAGE_DIR;
import static gitlet.Repository.ADD_FILE;
import static  gitlet.Repository.BLOB_DIR;
public class Blob implements Serializable {
    private String id;
    private String name;
    private String path;
    private byte[] bytes;
    File filecontent;

    public Blob(File file) throws IOException {
        name = file.getName();
        filecontent = file;
        id = GetBlobSHA1();

    }

    public String GetName() {
        return name;
    }
    public String GetId() {
        return id;
    }


    //得到SHA1
    public String GetBlobSHA1() {
        bytes=Utils.readContents(filecontent);
        return Utils.sha1( bytes,name);
    }

    // File f=Utils.join(OBJECTS_DIR,id.substring(0,2));
    //    f.mkdir();
    //    File f2=Utils.join(f.getAbsoluteFile(),id);
    //    f2.createNewFile();
    //    return f2.getAbsolutePath();加到缓/
    public String GetBlobPath() throws IOException {

        return path;
    }

    public void CreatBlobFolder() throws IOException {
        File f = Utils.join(BLOB_DIR, id.substring(0, 6));
        f.mkdir();
        File f2 = Utils.join(f.getAbsoluteFile(),id);
        f2.createNewFile();
        Utils.writeObject(f2,this);
        path = f2.getAbsolutePath();
    }
    public void RemoveBlob(){
      id=null;
      path=null;
      name=null;
      bytes=null;
      filecontent=null;
    }
    public byte[] Getbytes(){
        return bytes;
    }
}
