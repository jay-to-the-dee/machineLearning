/*
 */
package machinelearning;

import java.awt.Dimension;
import javax.swing.*;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;

/**
 *
 * @author jay-to-the-dee <jay-to-the-dee@users.noreply.github.com>
 */
public class JGraphFrame extends JFrame
{
    private Graph g;
    private JGraph jgraph;

    public JGraphFrame(Graph g)
    {
        super("Decision Tree Classifier");
        this.g = g;
        jgraph = new JGraph(new JGraphModelAdapter(g));
        jgraph.setPreferredSize(new Dimension(1024,768));
        
        this.add(jgraph);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
