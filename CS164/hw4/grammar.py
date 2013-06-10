# The Grammar:
#
#   S-> E | S,S
#   E-> E+E|E-E|E*E|(E)|-E|V
#   V-> a|b|c

# Original Grammar
G_org = {
    'S':('E','S,S'),                        \
    'E':('E+E','E-E','E*E','(E)','-E','V'), \
    'V':('a','b','c')                       \
   }

# Unambiguous Grammar
G_unamb={ '''unambiguous grammar''' }

# Fast Grammar
G_fast={ '''fast grammar''' }
