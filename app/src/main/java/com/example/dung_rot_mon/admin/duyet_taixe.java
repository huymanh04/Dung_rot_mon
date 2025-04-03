package com.example.dung_rot_mon.admin;

public class duyet_taixe {

        private String idTx;
        private String nameTx;
        private String phoneTx;
        private byte[] anh;

        // Constructor
        public duyet_taixe(String idTx, String nameTx, String phoneTx, byte[] anh) {
            this.idTx = idTx;
            this.nameTx = nameTx;
            this.phoneTx = phoneTx;
            this.anh = anh;
        }

        // Getter và Setter
        public String getIdTx() {
            return idTx;
        }

        public void setIdTx(String idTx) {
            this.idTx = idTx;
        }

        public String getNameTx() {
            return nameTx;
        }

        public void setNameTx(String nameTx) {
            this.nameTx = nameTx;
        }

        public String getPhoneTx() {
            return phoneTx;
        }

        public void setPhoneTx(String phoneTx) {
            this.phoneTx = phoneTx;
        }

        public byte[] getAnh() {
            return anh;
        }
        public void setAnh(byte[] anh) {
            this.anh = anh;
        }

}
