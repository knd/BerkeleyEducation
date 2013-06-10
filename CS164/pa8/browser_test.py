#!/usr/bin/python
import sys, browser, runtime
from urllib import urlopen
from optparse import OptionParser


def pretty_print(dom, out):
    def print_node(level, node):
        node_name = node["name"]

        node_str = ""
        node_attrs = ["klass", "background", "fract", "word"]
        for attr in node_attrs:       
            if attr in node:
                node_str += attr + "=\"" + str(node[attr]) + "\" "
        out.write(" " * level + "<" + node_name + " " + node_str[:-1] + ">\n")

        if "children" in node:
            children = node["children"]
            for index in children:
                if children[index]:
                    print_node(level + 1, children[index])
                else:
                    break

        out.write(" " * level + "</" + node_name + ">\n")
    print_node(0, dom)

if __name__ == '__main__':
    parser = OptionParser(usage='%prog [file...]')
    options, arguments = parser.parse_args()

    inputFile = arguments[0]
    script = arguments[1]
    outputFile = arguments[2]

    runtime.initialize()
    runtime.load(inputFile)
    dom = runtime._browser.dom
    
    with open(script, 'r') as f:
        script_text = f.read()

    with open(outputFile, 'w') as out:
        out.write("Before:\n")
        pretty_print(runtime._browser.dom, out)

        runtime._browser.execScript(script_text)
        
        out.write("After:\n")
        pretty_print(runtime._browser.dom, out)
