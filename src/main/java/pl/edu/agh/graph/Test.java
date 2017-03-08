package pl.edu.agh.graph;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Wojciech Pachuta.
 */
public class Test {
    public static void main(String[] args) {
//        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new SingleGraph("a graph");
        graph.addAttribute("ui.stylesheet", "url(file:///home/ugon/development/projects/gg-graph/src/main/resources/pl.edu.agh.graph/style.css)");

//        Viewer viewer = graph.display();
//        viewer.disableAutoLayout();
//        View defaultView = viewer.getView("defaultView");
//        defaultView.setMouseManager(new DoNothingMouseManager());

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel view = viewer.addDefaultView(false);

        ViewerPipe viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener(new ViewerListener() {
            @Override
            public void viewClosed(String s) {
            }

            @Override
            public void buttonPushed(String s) {
                System.out.println(s);
                Node node = graph.getNode(s);
                Node newNode = graph.addNode("node" + new Random().nextInt());
                newNode.addAttribute("x", (Integer) node.getAttribute("x") + 1);
                newNode.addAttribute("y", (Integer) node.getAttribute("y"));
                graph.addEdge("edge" + new Random().nextInt(), node, newNode);

            }

            @Override
            public void buttonReleased(String s) {
            }
        });
        viewerPipe.addSink(graph);

        Node node1 = graph.addNode("node1");
        Node node2 = graph.addNode("node2");
        Node node3 = graph.addNode("node3");
        Node node4 = graph.addNode("node4");

        Edge edge12 = graph.addEdge("edge12", node1, node2);
        Edge edge23 = graph.addEdge("edge23", node2, node3);
        Edge edge34 = graph.addEdge("edge34", node3, node4);
        Edge edge42 = graph.addEdge("edge41", node4, node1);

        node1.addAttribute("x", -1);
        node1.addAttribute("y", -1);
        node2.addAttribute("x", 1);
        node2.addAttribute("y", -1);
        node3.addAttribute("x", 1);
        node3.addAttribute("y", 1);
        node4.addAttribute("x", -1);
        node4.addAttribute("y", 1);

        node4.addAttribute("ui.class", "cls");

        SwingUtilities.invokeLater(() -> {
            JFrame myFrame = new JFrame();
            myFrame.setLayout(new BorderLayout());
            myFrame.add(view, BorderLayout.CENTER);
            myFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            myFrame.setSize(320, 240);
            myFrame.setLocationRelativeTo(null);

            myFrame.setVisible(true);
        });

        while (true) {
            viewerPipe.pump();
        }
    }
}
