package compiler.lelar.compiler;

import java.util.List;

public class CompilerEntity {
    private List<String> out;
    private List<String> err;

    public CompilerEntity(List<String> out, List<String> err) {
        this.out = out;
        this.err = err;
    }

    public CompilerEntity() {

    }

    public List<String> getOut() {
        return out;
    }

    public void setOut(List<String> out) {
        this.out = out;
    }

    public List<String> getErr() {
        return err;
    }

    public void setErr(List<String> err) {
        this.err = err;
    }

}
