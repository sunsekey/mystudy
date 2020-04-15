# Java基础概念
- 面向对象编程三大特性  
    封装、继承、多态

- 什么是多态?  
    分为**编译时多态**和**运行时多态**。如果在编译时能够确定执行多态方法中的哪一个，称为编译时多态，否则称为运行时多态。  
    方法重载表现的是编译时多态。根据实际参数的数据类型、个数和次序，Java在编译时能够确定执行重载方法中的哪一个，如：
     ```
        Person p = new Man();// Man为Person子类;
        p.toString();
        Man man = new Man();//
        man.toString()
     ```
    方法覆盖表现出两种多态性。当引用指向本类实例时，表现为编译时多态；当父类型引用指向子类实例时，则表现为多态。如：
     ```
        Person p = new Man();// Man为Person子类;
        p.toString();
     ```
- 什么是动态绑定
    在执行期间（而非编译期间），判断所引用对象的**实际类型**，根据实际的类型调用其相应的方法。  
        
- 什么是单一职责原则(Single-Resposibility Principle)？    
        一个类应该有且只有一个去改变它的理由，这意味着一个类应该只有一项工作。  
            
- 什么是开放封闭原则(Open-Closed principle)？  
    对扩展开放、对修改封闭
      
- 什么是里氏代换原则(Liskov-Substituion Principle)？  
    里氏代换原则(LSP)，任何基类可以出现的地方，子类一定可以出现。LSP是继承复用的基石。  
    
- 什么是接口隔离原则(Interface-Segregation Principle)？  
    客户端不应该被迫依赖它们不使用的方法。核心思想是使用多个小的专门的接口，而不要使用一个大的总接口。
    
- 什么是依赖倒置原则(Dependecy-Inversion Principle)？  
    依赖倒置原则(DIP)，本质是面向接口编程。模块之间不直接依赖，而是通过抽象来产生依赖。
        
        -每个类尽量都有接口或抽象类
        -任何类尽量不要派生于具体实现类
        -尽量不要重写抽象类中已经实现的方法  
        
- 什么是合成复用原则(Composite Reuse Principle)？  
    合成复用原则就是指在一个新的对象里通过关联关系（包括组合关系和聚合关系）来使用一些已有的对象，使之成为新对象的一部分；新对象通过委派调用已有对象的方法达到复用其已有功能的目的。简言之：**要尽量使用组合/聚合关系，少用继承**。
    
- 迪米特法则（最少知道原则,Demeter Principle）  
    一个实体应当尽量少的与其他实体之间发生相互作用，使得系统功能模块相对独立。这样，当一个模块修改时，就会尽量少的影响其他的模块，扩展会相对容易。

    
    