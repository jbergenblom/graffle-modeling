package org.epistem.diagram.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.epistem.graffle.OGGraphic;

/**
 * A table group
 *
 * @author nickmain
 */
public class Table extends Graphic implements GraphicContainer {

    public final Collection<Shape> cells = new HashSet<Shape>();
    
    /** Table[row][column] */
    public final Shape[][] table;
    
    /** @see java.lang.Iterable#iterator() */
    public Iterator<Graphic> iterator() {
        return new HashSet<Graphic>( cells ).iterator();
    }

    /** Get the row count */
    public int rowCount() { return table.length; }

    /** Get the column count */
    public int colCount() { return table[0].length; }
    
    /**
     * Accept a visitor
     */
    public void accept( DiagramVisitor visitor ) {
        DiagramVisitor cellVisitor = visitor.visitTableStart( this );
        
        if( cellVisitor != null ) {
            for( Graphic g : cells ) g.accept( cellVisitor );
        }
        
        visitor.visitTableEnd( this );
    }
    
    Table( OGGraphic ogg, GraphicContainer parent, Page page ) {
        super( ogg, parent, page );
        
        OGGraphic[][] oggTable = ogg.table();
        int colCount = oggTable.length;
        int rowCount = oggTable[0].length;
        
        table = new Shape[ rowCount ][ colCount ];
        
        for( int row = 0; row < rowCount; row++ ) {
            for( int col = 0; col < colCount; col++ ) {
                Shape s = new Shape( oggTable[ col ][ row ], this, page );
                table[ row ][ col ] = s;
                cells.add( s );
            }
        }
    }

    /** @see org.epistem.diagram.model.Graphic#init() */
    @Override
    void init() {
        //nothing
    }
    
    @Override
    public String toString() {
        return "Table";
    }
}
