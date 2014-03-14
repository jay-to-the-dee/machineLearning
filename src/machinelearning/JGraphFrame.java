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
    private final JGraphModelAdapter adapter;

    public JGraphFrame(Graph g)
    {
        super("Decision Tree Classifier");
        this.g = g;

        adapter = new JGraphModelAdapter(g);

        jgraph = new JGraph(adapter);
        jgraph.setPreferredSize(new Dimension(1024, 768));

        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.add(jgraph);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
