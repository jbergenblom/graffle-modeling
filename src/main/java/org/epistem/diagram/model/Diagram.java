package org.epistem.diagram.model;

import java.io.File;
import java.util.*;

import org.epistem.graffle.OGSheet;
import org.epistem.graffle.OmniGraffleDoc;

/**
 * An abstracted model of a diagram document
 *
 * @author nickmain
 */
public class Diagram {

    public final Map<String,Object> userData = new HashMap<String, Object>();
    
    public final File file;
    
    public final Collection<Page> pages = new HashSet<Page>();
    
    public final List<String> authors;
    public final List<String> keywords;
    public final List<String> languages;
    public final List<String> organizations;
    public final List<String> projects;

    public final String comments;
    public final String copyright;
    public final String description;
    public final String subject;
    public final String version;
    
    /**
     * Accept a visitor
     */
    public void accept( DiagramVisitor visitor ) {
        DiagramVisitor pageVisitor = visitor.visitDiagramStart( this );
        if( pageVisitor  != null ) {
            for( Page page : pages ) page.accept( pageVisitor );
        }
        
        visitor.visitDiagramEnd( this );
    }
    
    public Diagram( OmniGraffleDoc doc ) {
    
        file = doc.file();
        
        authors       = doc.authors();
        keywords      = doc.keywords();
        languages     = doc.languages();
        organizations = doc.organizations();
        projects      = doc.projects();
        comments      = doc.comments();
        copyright     = doc.copyright();
        description   = doc.description();
        subject       = doc.subject();
        version       = doc.version();        
        
        for( OGSheet sheet : doc.sheets() ) {        
            pages.add( new Page( sheet, this ) );
        }
    }
}
