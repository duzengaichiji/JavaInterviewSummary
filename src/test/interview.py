
def fun(arr):
    if not arr: return 0
    row = len(arr)
    col = len(arr[0])
    summation = [[0]*col for _ in range(row)]
    for i in range(row):
        summation[i][0] = arr[i][0]
        for j in range(1,col):
            summation[i][j] = summation[i][j-1]+arr[i][j]
    for i in range(1,row):
        summation[i] = [x+y for x,y in zip(summation[i],summation[i-1])]
    return summation

if __name__ == "__main__":
    testArr = [
        [3,7,1],
        [2,4,0],
        [9,4,2],
    ]
    print(fun(testArr))