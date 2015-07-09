package com.judahmarsh.parsing;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    PlaceHolderFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            taskFragment = new PlaceHolderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(taskFragment, "MyFragment").commit();
        } else {
            taskFragment = (PlaceHolderFragment) getSupportFragmentManager()
                    .findFragmentByTag("MyFragment");
        }
        taskFragment.startTask();
    }

    public static class PlaceHolderFragment extends Fragment {
        TechCrunchTask downloadTask;

        public PlaceHolderFragment() {

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            //never destroy this fragment
            setRetainInstance(true);
        }

        public void startTask() {
            if (downloadTask != null) {
                downloadTask.cancel(true);
            } else {
                downloadTask = new TechCrunchTask();
                downloadTask.execute();
            }
        }
    }

    public static class TechCrunchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String downloadUrl = "http://feeds.feedburner.com/techcrunch/android?format=xml";
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection)
                        url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                processXml(inputStream);

            } catch (Exception e) {
                L.m(e + "");
            }
            return null;
        }

        //this method proceses all xml
        public void processXml(InputStream inputStream) throws Exception {
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(inputStream);
            Element rootElement = xmlDoc.getDocumentElement();
            L.m("" + rootElement.getTagName());
            NodeList itemList = rootElement.getElementsByTagName("item");
            NodeList itemChildren;
            Node currentChild= null;
            Node currentItem;
            int count =0;

            //loop theough nodes
            for (int i = 0; i < itemList.getLength(); i++) {
                currentItem = itemList.item(i);
                itemChildren = currentItem.getChildNodes();
                for (int j = 0; j < itemChildren.getLength(); j++) {
                    currentChild = itemChildren.item(j);
                    if(currentChild.getNodeName().equalsIgnoreCase("title")){
                        //L.m(currentChild.getTextContent());
                    }

                    if (currentChild.getNodeName().equalsIgnoreCase("pubDate")){
                       // L.m(currentChild.getTextContent());
                    }
                    if (currentChild.getNodeName().equalsIgnoreCase("description")){
                       // L.m(currentChild.getTextContent());
                    }
                    if (currentChild.getNodeName().equalsIgnoreCase(
                            "media:thumbnail")){
                        count++;
                        if (count == 2) {
                            L.m(currentChild.getAttributes().item(0).getTextContent());
                        }
                    }
                }
                count =0;

            }
        }
    }
}