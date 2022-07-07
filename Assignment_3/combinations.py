# Dados n elementos genera los arreglos de indices correspondientes a todas las combinaciones de r elementos 
#  nCk = n! / (r! * (n - r)!)
# 
# 
# Pseudocodigo 
# iniciar con el arreglo base: un arreglo de indices desde 0 ... r-1
# desde el final del arreglo base:
#               Cada indice en el arreglo base puede llegar a incrementarse hasta el numero n - r + i donde i es la posicion de cada indice (0 ... r-i)         
#               Encontrar el primer elemento mas cercano al final de arreglo, qeu pueda ser incrementado (o sea que sea menor a n - r + i)
#               Una vez hallado el proximo indice a incrementar, incrementarlo en 1 
#               "Reiniciar" los numeros siguientes a ese indice. Los valores que deben de tener son, 
#                                                                                       apartir del ultimo numero incrementado i: 
#                                                                                                      Suponiendo que i = 2: [0 ... 2 3 4 5 ... r-1] 
#                                                                                                       Se incrementan los numeros hasta llegar a r-1
#               Guardar esa combinacion y repetir el proceso
#

# n = 6
# r = 4

# # para n, r = 4
# print("algoritmo 1") 

# for i in range(0, n-r+1):
#     for j in range(i+1, n-r+2):
#         for k in range(j+1, n-r+3):
#             for l in range(k+1, n):
#                 print(f'[{i}, {j}, {k}, {l}]')

import sys

def combinations(n, r):
    arr = [0]*r
    for i in range(r): arr[i] = i
    print(arr)
    while True:
        i = r - 1
        while i >= 0 and arr[i] == n - r + i: i-=1
        if i < 0: break
        arr[i] += 1
        for j in range(i+1, r): arr[j] = arr[j-1]+1
        print(arr)


if __name__ == "__main__":
    if (len(sys.argv) == 3):
        try:
            n = int(sys.argv[1])
            r = int(sys.argv[2])
        except:
            print("n & r must be numeric values")
            print("Usage: % python combinations.py n r")
        
        combinations(n, r)
    elif len(sys.argv) == 2:
        try:
            n = int(sys.argv[1])
        except:
            print("n must be a numeric value")
            print("Usage: % python combinations.py n")  
        
        for r in range(n):
            combinations(n, r)
    elif len(sys.argv) < 2:
        print("Usage: % python combinations.py n r")