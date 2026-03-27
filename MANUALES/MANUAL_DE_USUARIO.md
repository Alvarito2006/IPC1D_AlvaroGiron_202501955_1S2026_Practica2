# PRÁCTICA 2

**Curso:** Laboratorio de IPC1  
**Estudiante:** ALVARO MOISÉS GIRÓN MORALES  
**Carné:** 202501955  

---

# Manual de Usuario

## 1. Introducción

Bienvenido al **Visualizador de Algoritmos de Ordenamiento**. Esta aplicación le permite cargar listas de números, ejecutar tres algoritmos de ordenamiento (**Bubble Sort**, **Shell Sort** y **Quick Sort**) y observar en tiempo real cómo se ordenan los datos mediante una gráfica de barras. Además, podrá ver estadísticas detalladas como comparaciones, intercambios e iteraciones, un registro de operaciones paso a paso y generar reportes en formato HTML. 

El objetivo de esta aplicación es que el usuario comprenda visualmente el comportamiento de cada algoritmo y pueda comparar su rendimiento en diferentes condiciones.


---

## 2. Pantalla Principal

Al iniciar el programa, verá la ventana principal con tres opciones:

- **Visualizador de Algoritmos:** lleva al panel donde podrá cargar datos y ejecutar los algoritmos.
- **Ver Reportes:** muestra el historial de ejecuciones realizadas durante la sesión actual.
- **Ayuda:** presenta información general sobre la aplicación.

Para comenzar, haga clic en **Visualizador de Algoritmos**.

### Imagen de referencia

<img width="602" height="805" alt="image" src="https://github.com/user-attachments/assets/9b13b181-1119-4638-9ae0-6799b464d08f" />

---

## 3. Visualizador de Algoritmos

Esta es la ventana principal de trabajo. Se divide en varias áreas:

- **Panel de Control** (parte superior): permite cargar datos, seleccionar algoritmo, dirección de ordenamiento y velocidad.
- **Gráfica de Barras** (centro): muestra el estado actual del arreglo.
- **Estadísticas** (lateral o inferior): muestra en tiempo real comparaciones, intercambios e iteraciones.
- **Bitácora o Log** (parte inferior): lista paso a paso las operaciones realizadas.
- **Botón Iniciar Ordenamiento:** ejecuta el algoritmo con los parámetros elegidos.

### Imagen de referencia

<img width="284" height="404" alt="image" src="https://github.com/user-attachments/assets/e5e9380d-2c39-413d-ba23-f344fe6c777a" />


---

## 4. Carga de Datos

Puede cargar números de tres formas:

### 4.1 Desde un archivo de texto

1. Haga clic en **Cargar Archivo**.
2. Se abrirá un cuadro de diálogo.
3. Seleccione un archivo `.txt` que contenga números separados por comas o por saltos de línea.

#### Ejemplo de contenido válido

```text
45, 12, 78, 3, 29
```

o

```text
45
12
78
3
29
```
<img width="407" height="607" alt="image" src="https://github.com/user-attachments/assets/40236eae-8375-4c30-8165-0ada7202e1f3" />


Los números se cargarán automáticamente en el arreglo y se actualizará la gráfica. :contentReference[oaicite:8]{index=8}


<img width="406" height="608" alt="image" src="https://github.com/user-attachments/assets/fef7170f-896b-45d8-af38-5ea2e14c49ba" />

### 4.2 Desde el área de texto

1. Escriba directamente los números en el campo de texto, separados por comas.
2. Ejemplo:

```text
5, 2, 9, 1, 5, 6
```

3. Haga clic en **Cargar Manual** para validar y cargar los datos.

   <img width="401" height="601" alt="image" src="https://github.com/user-attachments/assets/b760c3e8-7726-4908-ad79-d03f464e3f71" />


### 4.3 Generar arreglo aleatorio

Si lo desea, puede generar un arreglo aleatorio haciendo clic en **Generar Aleatorio**.

Luego deberá ingresar:

- tamaño del arreglo
- valor mínimo
- valor máximo 

---

<img width="402" height="604" alt="image" src="https://github.com/user-attachments/assets/ce956a5f-3715-41c3-a06d-292c725fda3a" />


---
## 5. Configuración del Ordenamiento

Antes de ejecutar el algoritmo, seleccione las siguientes opciones:

### 5.1 Algoritmo

- **Bubble Sort:** método iterativo que compara elementos adyacentes.
- **Shell Sort:** ordenamiento por intervalos, más rápido que Bubble Sort en muchos casos.
- **Quick Sort:** algoritmo recursivo basado en particiones, usualmente el más rápido de los tres.

  <img width="407" height="607" alt="image" src="https://github.com/user-attachments/assets/fd6f50f8-72e0-4bdc-b0e9-041142fd4bc0" />


### 5.2 Orden

- **Ascendente:** de menor a mayor.
- **Descendente:** de mayor a menor.

  <img width="403" height="601" alt="image" src="https://github.com/user-attachments/assets/1144e998-1668-437f-84e2-07a13ccccd22" />


### 5.3 Velocidad

- **Lento:** 500 ms entre cada paso.
- **Medio:** 100 ms.
- **Rápido:** 20 ms. 


<img width="400" height="607" alt="image" src="https://github.com/user-attachments/assets/89929891-a45c-4ac8-b0a0-b453ad4cf094" />

---


## 6. Ejecutar el Ordenamiento

Para iniciar el proceso:

1. Asegúrese de que los datos estén cargados.
2. Seleccione el algoritmo.
3. Elija el tipo de orden.
4. Defina la velocidad.
5. Haga clic en el botón **Iniciar**.

Durante la ejecución podrá observar lo siguiente:

- La gráfica de barras cambia en cada paso.
- Las estadísticas se actualizan en tiempo real.
- El log muestra cada comparación e intercambio realizado.

Al finalizar:

- Se mostrará un mensaje en el log indicando el tiempo total de ejecución.
- Se generará automáticamente un reporte HTML.

<img width="600" height="405" alt="image" src="https://github.com/user-attachments/assets/61992cc7-db4e-4ebb-9bc7-97b1690ffc46" />


### Importante

Durante la ejecución, el botón **Iniciar** se deshabilita para evitar interferencias. Puede detener el proceso cerrando la ventana o iniciando una nueva ejecución, lo cual cancelará la anterior. :contentReference[oaicite:17]{index=17}

---

## 7. Interpretación de la Bitácora

Cada entrada en la bitácora tiene un formato similar al siguiente:

```text
[Paso X] Operación: arr[indice1]=valor1 con arr[indice2]=valor2 → resultado
```

### Ejemplos

```text
[Paso 12] Comparando arr[3]=25 con arr[4]=12 → intercambio realizado
```

```text
[Paso 15] Partición completada: pivote en índice 8
```

Al finalizar el proceso, se mostrará un mensaje como este:

```text
Ordenamiento completado en 1234 ms
``` 
<img width="407" height="238" alt="image" src="https://github.com/user-attachments/assets/331ece0d-cc74-43d7-8b2e-5f99a6524009" />


---

## 8. Reportes

### 8.1 Resumen HTML

Al terminar cada ejecución, se guarda automáticamente un archivo HTML en la carpeta `reports/`, ubicada dentro de la misma carpeta donde se ejecuta la aplicación. El archivo contiene:

- algoritmo y orden utilizados
- arreglo original y arreglo ordenado
- número de comparaciones
- número de intercambios
- número de iteraciones
- tiempo total de ejecución
- velocidad seleccionada

Estos archivos pueden abrirse con cualquier navegador web.

<img width="809" height="961" alt="image" src="https://github.com/user-attachments/assets/bb415623-a7da-485c-a0a3-3931ee410ee8" />


### 8.2 Historial de la sesión

Desde el menú principal, seleccione **Ver Reportes**. Se mostrará una tabla con todas las ejecuciones realizadas desde que inició la aplicación.

Cada fila incluye:

- número de ejecución
- algoritmo
- orden
- tamaño del arreglo
- comparaciones
- intercambios
- iteraciones
- tiempo en milisegundos

<img width="403" height="604" alt="image" src="https://github.com/user-attachments/assets/b3ffe0d4-f090-42d0-8af6-eb8504cdbfd5" />


Esta información se pierde al cerrar la aplicación. 

<img width="400" height="530" alt="image" src="https://github.com/user-attachments/assets/757b4e04-543c-49f4-ac1a-499a631f09c7" />


---

## 9. Consejos y Buenas Prácticas

- Para entender mejor un algoritmo, utilice la velocidad **lenta** y observe cuidadosamente cómo cambian las barras.
- Compare la cantidad de comparaciones e intercambios entre **Bubble Sort** y **Quick Sort** utilizando el mismo arreglo.
- Utilice arreglos de tamaño moderado para evitar saturar la bitácora.
- Guarde los reportes HTML como evidencia de sus pruebas. 

---

## 10. Solución de Problemas

| Problema | Posible solución |
|---|---|
| El programa no se abre | Verifique que Java esté instalado. Puede comprobarlo ejecutando `java -version` en una terminal. |
| Al cargar un archivo no pasa nada | Asegúrese de que el archivo contenga números válidos y que esté en un formato correcto. |
| La gráfica no se actualiza | Verifique que los datos se hayan cargado correctamente. Reinicie la aplicación si el problema persiste. |
| El ordenamiento no termina | Si el arreglo es muy grande y la velocidad es lenta, espere unos segundos más. También puede cerrar y volver a abrir la aplicación. |
| No se generan reportes | Compruebe que la carpeta `reports/` tenga permisos de escritura. El programa la crea automáticamente. | 

---

## 11. Recomendaciones Finales

Se recomienda probar los tres algoritmos con distintos tamaños de arreglo, diferentes órdenes y velocidades para comprender mejor cómo cambia su comportamiento. El uso combinado de la gráfica, las estadísticas y la bitácora permite analizar de forma clara el proceso de ordenamiento.



---
