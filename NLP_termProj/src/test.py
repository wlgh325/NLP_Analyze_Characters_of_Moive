import networkx as nx
import matplotlib.pyplot as plt
import sys

print("start")
print(sys.argv[1])
def draw_network2(filename):
    f = open(filename, "r")
    lines =[]
    scene_delimiter = '#'
    weight_delimiter = '\t'
    size_delimimter = '\t'
    node_delimiter = '-'
    temp_line = ''
    edge_size = 20
    flag = 1

    nodes = []
    edges = []
    weights = []
    nodeSizes = []
    normalized_weights = []
    temp_lines = f.readlines()

    for line in temp_lines:
        if line[-1] == '\n':
            lines.append(line[:-1])
        else:
            lines.append(line)


    f.close()


    # make Graph information
    for line in lines:
        if line == 'NODE':
            flag = 0

        if '\t' in line:
            if flag != 0:
                temp = line.split(weight_delimiter)
                temp_nodes = temp[0]
                temp_weight = temp[1]

                # split node
                temp_nodes = temp[0].split(node_delimiter)
                temp_node1 = temp_nodes[0]
                temp_node2 = temp_nodes[1]

                # add edge
                temp_edge = (temp_node1, temp_node2)
                edges.append(temp_edge)
                temp_weight=float(temp_weight) * 10
                # add weight
                weights.append(temp_weight)
            else:
                # add node with size
                node_info = line.split(size_delimimter)
                nodes.append(node_info[0])
                size = float(node_info[1])
                nodeSizes.append(size)

    min_w = min(weights)
    max_w = max(weights)

    for weight in weights:
        normalized_weights.append((4*(weight - min_w)) / (max_w - min_w)  + 1)

    G = nx.Graph()

    color_list = ['r', 'g', 'b', 'lime', 'deeppink']

    for i in range(len(edges)):
        G.add_edge(edges[i][0], edges[i][1], weight=normalized_weights[i], color=color_list[i%len(color_list)])

    for i in range(len(nodes)):
        G.add_node(nodes[i], node_size=nodeSizes[i]*5)

    temp_nodes = G.nodes()
    temp_edges = G.edges()
    colors = [G[u][v]['color'] for u,v in temp_edges]
    normalized_weights = [G[u][v]['weight'] for u,v in temp_edges]
    nodeSizes = [G.nodes[u]['node_size'] for u in temp_nodes]

    #nx.draw_spring(G, with_labels=True, node_size=nodeSizes, font_weight='bold', font_size=10, edge_color=colors, width=weights, font_family='sans-serif')
    #nx.draw_kamada_kawai(G, with_labels=True, node_size=nodeSizes, font_weight='bold', font_size=10, edge_color=colors, width=weights, font_family='sans-serif')
    #nx.draw_circular(G, with_labels=True, node_`size=nodeSizes, font_weight='bold', font_size=10, edge_color=colors, width=weights, font_family='sans-serif')
    #nx.draw_spectral(G, with_labels=True, node_size=nodeSizes, font_weight='bold', font_size=10, edge_color=colors, width=weights, font_family='sans-serif')
    nx.draw_shell(G, with_labels=True, node_size=nodeSizes, font_weight='bold', font_size=10, edge_color=colors, width=normalized_weights, font_family='sans-serif')
    plt.show()
    print("finished")

draw_network2(sys.argv[1])
