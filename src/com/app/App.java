package com.app;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class App {
    private JTable table1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton searchButton;
    private JButton loadDataButton;

    private DefaultTableModel _model;
    private Forcast _forcast;

    void previewData(Stream<Datum> data){
        if (_model != null){
            _model.setRowCount(0);
            data.forEach(d -> {
                Vector fields = new Vector();
                fields.add(d.getDatetime());
                fields.add(d.getWeather().getDescription());
                fields.add(d.getTemp());

                _model.addRow(fields);
            });
        }
    }


    void fetchData(){
        CompletableFuture.supplyAsync(() -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://weatherbit-v1-mashape.p.rapidapi.com/forecast/daily?lat=46.469391&lon=30.740883"))
                    .header("x-rapidapi-key", "1c36ba4ff3msh7b78410e81cc37fp1ae6bajsndc32ec92ca8f")
                    .header("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                _forcast = new ObjectMapper().reader(Forcast.class).readValue(json);
                System.out.println(_forcast.toString());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            previewTable();
            return null;
        });
    };

    void previewTable(){
        if (_forcast == null)
            return;

        Vector headers = new Vector();
        headers.add("datetime");
        headers.add("weather");
        headers.add("temp");

        _model = new DefaultTableModel(0, headers.size());
        _model.setColumnIdentifiers(headers);

        previewData(_forcast.getData().stream());

        table1.setModel(_model);
    }

    boolean compareDataTokens(String s1, String[] t2){
        for (String s2 : t2){
            if (s1.contains(s2) || s2.contains(s1))
                return true;
        }
        return false;
    }

    boolean compareAllTokens(String[] t1, String[] t2){
        for (String s1 : t1){
            if (!compareDataTokens(s1, t2))
                return false;
        }
        return true;
    }

    public static String[] merge(String[] ...arrays)
    {
        return Stream.of(arrays)
                .flatMap(Stream::of)
                .toArray(String[]::new);
    }

    void search(){
        if (_forcast == null)
            return;

        String[] searchTokens = textField1.getText().split(" ");
        previewData(_forcast.getData().stream().filter(datum -> {
            String[] dataTokens = merge(
                    datum.getDatetime().split(" "),
                    datum.getWeather().getDescription().split(" "),
                    String.valueOf(datum.getTemp()).split(" ")
            );

            return compareAllTokens(searchTokens, dataTokens);
        }).sorted(Comparator.comparing(Datum::getDatetime)));
    }

    public App() {

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
