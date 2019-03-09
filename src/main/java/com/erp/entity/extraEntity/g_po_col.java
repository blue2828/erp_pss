package com.erp.entity.extraEntity;

public class g_po_col implements java.io.Serializable {
    private int g_id, p_o_id, repoId;

    public g_po_col() {
    }

    public g_po_col(int g_id, int p_o_id, int repoId) {
        this.g_id = g_id;
        this.p_o_id = p_o_id;
        this.repoId = repoId;
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public int getP_o_id() {
        return p_o_id;
    }

    public void setP_o_id(int p_o_id) {
        this.p_o_id = p_o_id;
    }
}
