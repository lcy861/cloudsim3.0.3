package org.cloudbus.cloudsim.util;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collections;  
import java.util.Comparator;  
import java.util.Date;  
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Helper;
import org.cloudbus.cloudsim.examples.power.planetlab.NonPowerAware;
import org.cloudbus.cloudsim.examples.power.planetlab.PlanetLabConstants;
import org.cloudbus.cloudsim.examples.power.planetlab.PlanetLabHelper;
import org.cloudbus.cloudsim.power.PowerHost;  
  
/** 
 * admin<br> 
 * 通用工具类之按对象中某属性排序 
 *  
 */  
public class SortListUtil {  
    public static final String DESC = "desc";  
    public static final String ASC = "asc";  
    
    public static void main(String[] args) throws FileNotFoundException {
    	
    	String inputFolder = NonPowerAware.class.getClassLoader().getResource("workload/planetlab/20110303")
				.getPath();

		CloudSim.init(1, Calendar.getInstance(), false);
    	DatacenterBroker broker = Helper.createBroker();
		int brokerId = broker.getId();
    	List<Cloudlet> cloudletList = PlanetLabHelper.createCloudletListPlanetLab(brokerId, inputFolder);
    	List<Vm> vmList = Helper.createVmList(brokerId, cloudletList.size());
		List<PowerHost> hostList = Helper.createHostList(PlanetLabConstants.NUMBER_OF_HOSTS);
		
		for(PowerHost host : hostList) {
			System.out.println(host.getId() + ": " + host.getAvailableMips());
		}
		
		sortByAvailableMips(hostList);
		System.out.println("**************************");
		for(PowerHost host : hostList) {
			System.out.println(host.getId() + ": " + host.getAvailableMips());
		}
    }
  
    //按可用mips升序排主机
    public static void sortByAvailableMips(List<?> list) {
    	Collections.sort(list, new Comparator<Object>() {
    		@Override  
            public int compare(Object o1, Object o2) {  
    			PowerHost host1 = (PowerHost) o1;  
    			PowerHost host2 = (PowerHost) o2;  
                if (host1.getAvailableMips() > host2.getAvailableMips()) {  
                    return 1;  
                } else if (host1.getAvailableMips() == host2.getAvailableMips()) {  
                    return 0;  
                } else {  
                    return -1;  
                }  
            }  
    	});
    }
    
    //按mips降序排虚拟机
    public static void sortByVmMips(List<?> list) {
    	Collections.sort(list, new Comparator<Object>() {

    		@Override
    		public int compare(Object o1, Object o2) {
    			Vm vm1 = (Vm) o1;
    			Vm vm2 = (Vm) o2;
    			if(vm1.getMips() > vm2.getMips()) {
    				return -1;
    			}else if(vm1.getMips() == vm2.getMips()) {
    				return 0;
    			}else {
    				return 1;
    			}
    		}
    	});
    }
}  





