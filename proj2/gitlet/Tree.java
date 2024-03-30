package gitlet;

import java.util.LinkedList;

public class Tree {
    private String id;
    private LinkedList<String> blobs;
    public void Tree(){

    }
    private void Tree(LinkedList<Blob> blobslist){
        blobs=GetBlobsId(blobslist);
        id=GetTreeSHA1();
    }
    public LinkedList<String> GetBlobsId(LinkedList<Blob> blobslist){
        LinkedList<String> blobsid=new LinkedList<>();
        for(Blob b:blobslist){
            blobsid.add(b.GetBlobSHA1());
        }
        return blobsid;
    }
    public String GetTreeSHA1(){
        return  Utils.sha1(blobs);
    }
}
