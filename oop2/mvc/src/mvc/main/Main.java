/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.main;

import mvc.controler.WurzelController;
import mvc.model.WurzelModel;
import mvc.view.WurzelView;

/**
 *
 * @author sgross8
 */
public class Main {

    static WurzelController controller;
    static WurzelModel model;
    static WurzelView view;

    /**
     * Diese Klasse wird nur dazu benutzt alle nötigen
     * Komponenten zu Initialisieren und die erste
     * View anzuzeigen
     */
    public static void main(String [] args){

        model = new WurzelModel();
        view = new WurzelView();
        controller = new WurzelController(view, model);

        view.setVisible(true);
    }
}
