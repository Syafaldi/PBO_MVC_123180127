package pbo_mvc;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class MhsController {
    MhsModel mhsmodel;
    MhsView mhsview;
    MhsDAO mhsdao;
    String nim2;
    
    public MhsController (MhsModel mhsmodel, MhsView mhsview, MhsDAO mhsdao){
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;
        
        if(mhsdao.getJmldata()!=0){
            String dataMahasiswa[][] = mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa,mhsview.namaKolom)).getModel());
        }else{
            JOptionPane.showMessageDialog(null,"Data tidak ada");
        }
        
        mhsview.simpan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                if(nim.isEmpty()||nama.isEmpty()||alamat.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Harap isi semua field");
                }else{
                    mhsmodel.setMhsModel(nim,nama,alamat);
                    mhsdao.Insert(mhsmodel);
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable(dataMahasiswa,mhsview.namaKolom)).getModel());
                }
            }
            });
        
        mhsview.tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                 int baris = mhsview.tabel.rowAtPoint(evt.getPoint());
                 nim2 = mhsview.tabel.getValueAt(baris,1).toString();
            }
        });
        
        mhsview.delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mhsmodel.setMhsModel(nim2, null, null);
                mhsdao.Delete(mhsmodel);
                String dataMahasiswa[][] = mhsdao.readMahasiswa();
                mhsview.tabel.setModel((new JTable(dataMahasiswa,mhsview.namaKolom)).getModel());
                
            }
        });
    }
}

