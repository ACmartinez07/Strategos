package com.visiongc.framework.test;

import com.visiongc.framework.impl.FrameworkServiceFactory;

public class Test2
{

    public Test2()
    {
    }

    public static void main(String args[])
    {
        com.visiongc.framework.arboles.ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();
    }
}