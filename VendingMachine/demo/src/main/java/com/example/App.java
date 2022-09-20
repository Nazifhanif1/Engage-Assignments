package com.example;

import com.example.controller.VendingMachine_Controller;
import com.example.dao.VendingMachineAuditDao;
import com.example.dao.VendingMachineAuditFileImpl;
import com.example.dao.VendingMachine_DAO;
import com.example.dao.VendingMachine_daoFileImpl;
import com.example.service.VendingMachineServiceLayer;
import com.example.service.VendingMachineServiceLayerImpl;
import com.example.ui.UserIOConsoleImpl;
import com.example.ui.UserIo;
import com.example.ui.VendingMachineView;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {

        UserIo myIo = new UserIOConsoleImpl();
        VendingMachine_DAO myDao = new VendingMachine_daoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditFileImpl();
        VendingMachineServiceLayer myServiceLayer = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachine_Controller Controller = new VendingMachine_Controller(myView, myServiceLayer);
        Controller.run();
    }
}
