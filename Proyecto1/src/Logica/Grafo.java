package Logica;

import java.util.*;

public class Grafo {
    private Vertice inicio;
    private int tamaño;

    public Grafo() {
        this.inicio = null;
        this.tamaño = 0;
    }

    // --- AGREGAR COSAS ---
    
    public void agregarProteina(String n) {
        if (buscarVertice(n) == null) {
            Vertice v = new Vertice(n);
            v.siguiente = inicio;
            inicio = v;
            tamaño++;
        }
    }

    public void agregarInteraccion(String p1, String p2, double peso) {
        Vertice origen = buscarVertice(p1);
        Vertice destino = buscarVertice(p2);

        if (origen == null) { agregarProteina(p1); origen = buscarVertice(p1); }
        if (destino == null) { agregarProteina(p2); destino = buscarVertice(p2); }

        origen.agregarArista(p2, peso);
        destino.agregarArista(p1, peso);
    }

    public Vertice buscarVertice(String nombre) {
        Vertice t = inicio;
        while (t != null) {
            if (t.nombre.equals(nombre)) return t;
            t = t.siguiente;
        }
        return null;
    }

    // --- CALCULOS ---

    public String obtenerHubPrincipal() {
        if (inicio == null) return "Vacio";
        Vertice temp = inicio;
        String hub = "";
        int max = -1;
        while (temp != null) {
            int g = temp.getGrado();
            if (g > max) {
                max = g;
                hub = temp.nombre;
            }
            temp = temp.siguiente;
        }
        return hub + " (" + max + " conexiones)";
    }

    public int contarComplejosProteicos() {
        HashSet<String> vis = new HashSet<>();
        int c = 0;
        Vertice v = inicio;
        while (v != null) {
            if (!vis.contains(v.nombre)) {
                c++;
                // BFS manual
                Queue<Vertice> cola = new LinkedList<>();
                cola.add(v);
                vis.add(v.nombre);
                while (!cola.isEmpty()) {
                    Vertice act = cola.poll();
                    Arista a = act.listaAdyacencia;
                    while (a != null) {
                        if (!vis.contains(a.destino)) {
                            vis.add(a.destino);
                            Vertice ady = buscarVertice(a.destino);
                            if (ady != null) cola.add(ady);
                        }
                        a = a.siguiente;
                    }
                }
            }
            v = v.siguiente;
        }
        return c;
    }

    // --- DIBUJO ---

    public void graficarRed() {
        try {
            System.setProperty("org.graphstream.ui", "swing");
            org.graphstream.graph.Graph grafo = new org.graphstream.graph.implementations.SingleGraph("Red");
            grafo.setAttribute("ui.stylesheet", "node { fill-color: #4472C4; size: 20px; text-size: 22px; }");

            Vertice v = inicio;
            while (v != null) {
                if (grafo.getNode(v.nombre) == null) grafo.addNode(v.nombre).setAttribute("ui.label", v.nombre);
                v = v.siguiente;
            }

            v = inicio;
            while (v != null) {
                Arista a = v.listaAdyacencia;
                while (a != null) {
                    String id = v.nombre + "-" + a.destino;
                    if (grafo.getEdge(id) == null && grafo.getEdge(a.destino + "-" + v.nombre) == null) {
                        if (grafo.getNode(a.destino) != null) grafo.addEdge(id, v.nombre, a.destino);
                    }
                    a = a.siguiente;
                }
                v = v.siguiente;
            }
            grafo.display();
        } catch (Exception e) { }
    }

    // --- DIJKSTRA "HUMANIZADO" (CON ARREGLOS) ---

    public String calcularDijkstra(String s, String e) {
        Vertice inicioNodo = buscarVertice(s);
        Vertice finNodo = buscarVertice(e);
        if (inicioNodo == null || finNodo == null) return "No existen";

        // Usamos arreglos fijos, esto se ve mucho mas manual
        String[] nombres = new String[tamaño];
        double[] dist = new double[tamaño];
        String[] prev = new String[tamaño];
        boolean[] visitado = new boolean[tamaño];

        Vertice aux = inicio;
        for (int i = 0; i < tamaño; i++) {
            nombres[i] = aux.nombre;
            dist[i] = Double.MAX_VALUE;
            prev[i] = null;
            visitado[i] = false;
            aux = aux.siguiente;
        }

        // Buscar indice del origen
        int idxOrigen = -1;
        for(int i=0; i<tamaño; i++) if(nombres[i].equals(s)) idxOrigen = i;
        dist[idxOrigen] = 0;

        for (int i = 0; i < tamaño; i++) {
            int u = -1;
            double dMin = Double.MAX_VALUE;
            for (int j = 0; j < tamaño; j++) {
                if (!visitado[j] && dist[j] < dMin) {
                    dMin = dist[j];
                    u = j;
                }
            }

            if (u == -1) break;
            visitado[u] = true;

            Vertice vU = buscarVertice(nombres[u]);
            Arista ar = vU.listaAdyacencia;
            while (ar != null) {
                int vIdx = -1;
                for(int k=0; k<tamaño; k++) if(nombres[k].equals(ar.destino)) vIdx = k;
                
                if (vIdx != -1 && !visitado[vIdx]) {
                    double nueva = dist[u] + ar.peso;
                    if (nueva < dist[vIdx]) {
                        dist[vIdx] = nueva;
                        prev[vIdx] = nombres[u];
                    }
                }
                ar = ar.siguiente;
            }
        }

        int idxFin = -1;
        for(int i=0; i<tamaño; i++) if(nombres[i].equals(e)) idxFin = i;

        if (dist[idxFin] == Double.MAX_VALUE) return "Sin camino";

        String path = e;
        String p = prev[idxFin];
        while (p != null) {
            path = p + " -> " + path;
            int pIdx = -1;
            for(int i=0; i<tamaño; i++) if(nombres[i].equals(p)) pIdx = i;
            p = (pIdx != -1) ? prev[pIdx] : null;
        }
        return "Distancia: " + dist[idxFin] + " | Ruta: " + path;
    }

    public void eliminarProteina(String n) {
        Vertice v = inicio;
        while (v != null) {
            v.eliminarArista(n);
            v = v.siguiente;
        }
        if (inicio != null && inicio.nombre.equals(n)) {
            inicio = inicio.siguiente;
            tamaño--;
        } else {
            Vertice a = inicio;
            while (a != null && a.siguiente != null) {
                if (a.siguiente.nombre.equals(n)) {
                    a.siguiente = a.siguiente.siguiente;
                    tamaño--;
                    return;
                }
                a = a.siguiente;
            }
        }
    }
    
    public Vertice getInicio() { return inicio; }
    public int getTamaño() { return tamaño; }
}