import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class LinkRepository {
    private final MongoCollection<Document> links;
    private final static String ID = "_id";
    private final static String URL = "url";
    private final static String DESCRIPTION = "description";
    public LinkRepository(MongoCollection<Document> links){
        this.links = links;
    }

    public Link findById(String id){
        Document doc = links.find(eq(ID, new ObjectId(id))).first();
        return link(doc);
    }

    public List<Link> getAllLinks(){
        List<Link> allLinks = new ArrayList();
        for (Document doc: links.find()){
            allLinks.add(link(doc));
        }
        return  allLinks;
    }

    public void saveLink(Link link){
        Document doc = new Document();
        doc.append(URL, link.getUrl());
        doc.append(DESCRIPTION, link.getDescription());
        links.insertOne(doc);
    }

    private Link link(Document doc){
        return  new Link(doc.get(ID).toString(), doc.get(URL).toString(), doc.get(DESCRIPTION).toString());
    }
}
