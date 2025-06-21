import java.io.Serializable;
import java.util.List;

public class Manga implements Serializable {

    private String isbn;
    private String titulo;
    private String autores;
    private String anoInicio;
    private String anoFim;
    private String genero;
    private String revista;
    private String editora;
    private String anoEdicao;
    private int qtdVolumes;
    private int qtdVolumesAdquiridos;
    private List<Integer> volumesAdquiridos;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(String anoInicio) {
        this.anoInicio = anoInicio;
    }

    public String getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(String anoFim) {
        this.anoFim = anoFim;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRevista() {
        return revista;
    }

    public void setRevista(String revista) {
        this.revista = revista;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAnoEdicao() {
        return anoEdicao;
    }

    public void setAnoEdicao(String anoEdicao) {
        this.anoEdicao = anoEdicao;
    }

    public int getQtdVolumes() {
        return qtdVolumes;
    }

    public void setQtdVolumes(int qtdVolumes) {
        this.qtdVolumes = qtdVolumes;
    }

    public int getQtdVolumesAdquiridos() {
        return qtdVolumesAdquiridos;
    }

    public void setQtdVolumesAdquiridos(int qtdVolumesAdquiridos) {
        this.qtdVolumesAdquiridos = qtdVolumesAdquiridos;
    }

    public List<Integer> getVolumesAdquiridos() {
        return volumesAdquiridos;
    }

    public void setVolumesAdquiridos(List<Integer> volumesAdquiridos) {
        this.volumesAdquiridos = volumesAdquiridos;
    }

    public String toLine() {
        return String.format(
            "ISBN: %s;\n" +
            "TÍTULO: %s;\n" +
            "AUTORES: %s;\n" +
            "ANO DE INÍCIO: %s;\n" +
            "ANO DE FINALIZAÇÃO: %s;\n" +
            "GÊNEROS: %s;\n" +
            "REVISTA: %s;\n" +
            "EDITORA: %s;\n" +
            "ANO DE EDIÇÃO: %s;\n" +
            "QUANTIDADE DE VOLUMES: %d;\n" +
            "QUANTIDADE DE VOLUMES ADQUIRIDOS: %d;\n" +
            "VOLUMES ADQUIRIDOS: %s",
            isbn,
            titulo,
            autores,
            anoInicio,
            anoFim,
            genero,
            revista,
            editora,
            anoEdicao,
            qtdVolumes,
            qtdVolumesAdquiridos,
            volumesAdquiridos.toString()
        );
    }
}
