package com.example.miprimeraaplicacion;

public class productos {
    String idProducto;
    String codigo;
    String descripcion;
    String marca;
    String presentacion;
    String precio;
    String costo; // Nuevo campo agregado
    String gananciaPorcentaje; // Nuevo campo para almacenar el porcentaje de ganancia
    String foto;
    String foto1;
    String foto2;

    // Constructor actualizado
    public productos(String idProducto, String codigo, String descripcion, String marca,
                     String presentacion, String precio, String costo, String foto,
                     String foto1, String foto2) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.costo = costo;
        this.foto = foto;
        this.foto1 = foto1;
        this.foto2 = foto2;
        // Calculamos automáticamente la ganancia al crear el objeto
        this.gananciaPorcentaje = calcularGananciaPorcentaje(precio, costo);
    }

    // Método para calcular el porcentaje de ganancia
    private String calcularGananciaPorcentaje(String precioStr, String costoStr) {
        try {
            double precio = Double.parseDouble(precioStr);
            double costo = Double.parseDouble(costoStr);

            if (costo == 0) return "0"; // Evitar división por cero

            double ganancia = ((precio - costo) / costo) * 100;
            return String.format("%.2f", ganancia); // Formatear a 2 decimales
        } catch (NumberFormatException e) {
            return "0"; // Retornar 0 si hay error en los números
        }
    }

    // Getters y Setters para los nuevos campos
    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
        // Recalculamos la ganancia al actualizar el costo
        this.gananciaPorcentaje = calcularGananciaPorcentaje(this.precio, costo);
    }

    public String getGananciaPorcentaje() {
        return gananciaPorcentaje;
    }

    // No debería haber setter para gananciaPorcentaje ya que se calcula automáticamente

    // Resto de los getters y setters existentes...
    public String getidProducto() {
        return idProducto;
    }

    public void setidProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getcodigo() {
        return codigo;
    }

    public void setcodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getmarca() {
        return marca;
    }

    public void setmarca(String marca) {
        this.marca = marca;
    }

    public String getpresentacion() {
        return presentacion;
    }

    public void setpresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getprecio() {
        return precio;
    }

    public void setprecio(String precio) {
        this.precio = precio;
        // Recalculamos la ganancia al actualizar el precio
        this.gananciaPorcentaje = calcularGananciaPorcentaje(precio, this.costo);
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }
}