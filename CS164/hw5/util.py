_nextNum = 0
def uniqueIdentifier ():
    '''Return an identifier that has never before been returned by
    uniqueIdentifier ().
    '''
    global _nextNum
    _nextNum += 1
    return 'id%d'%(_nextNum)
