##Introduction
This is a java program providing basic interfaces for interactions with the [gMission](https://github.com/gmission) server on a computer; those interfaces are wrapped within a class named HttpRequest. Moreover, it implements a demo which supports posting HITs of different types , followed by manually giving answers by interacting with the console or dialogs. 

The project contains four classes:

+ **Main**：a demo which supports posting HITs and their answers by interacting with the console or dialogs, as mentioned in last paragraph. 
+ **HttpRequest**: an agent class which provides basic interfaces for interacting with the gMisssion server, including sending **Get** and **Post** requests, based on which it furthers implement some other interfaces such as `getDefinitionList()`.
+ **RandomAgent**: an agent which is responsible for randomly picking a registered user or posted location, which are then used to post/answer questiom in the demo.
+ **FileAgent**: an agent which is responsible for showing a dialog asking selecting an image when answering a HIT with image during execution ofthe demo.

##Requirements
All the classes are implemented using **Java** language, and therefore [Java JDK](http://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/index.html) is required. You can run the demo using an IDE like [Eclipse](https://eclipse.org/downloads/). An external library [JSON](http://www.JSON.org/) is needed since  the Content-Type of **GET/POST** requests is JSON. The JSON library is wrapped in the package *org.json*, which is imported in all the other class files to support JSON operations.

##How to use it
The demo enables the user to post two types of HITs in terms of the way of giving answers, i.e., options and brief answers. Please et proper parameters in the front part of the main function before runninf the demo. The common parameter for both types of HIT are:

+ **hit_title**: the title of your HIT
+ **hit_intro**: the content of your HIT
+ **credit**: the reward for answering your HIT
+ **num_ans**: the targetted number of answers

#### HIT_TYPE_SELECTION
 You need to set `hit_type = HIT_TYPE_SELECTION`, which would allow to post a multi-choice HIT.There are some other parameters to be adjusted for this type of HIT:
 
 + **min_select_cnt**: the minimum number of selected choices for an answer.
 + **max_select_cnt**: the maiximum number of selected choices for an answer.
 + **choices**: all the possible choices of your HIT, for example: `choices = new String[]{"very good","good","soso","bad"}`
 
 Remark: `0 < min_select_cnt <= max_select_cnt <= choices.length`.  After this type of hit has been posted, a dialog will show up and ask you to input the brief for an answer. It is not necessary for you to post answers for that question, so if you don’t want to do so, just press the “cancel“ button, and the program would end with posting no answers. But if you type in some contents, then another dialog would show up for you to select a picture for that answer. Again if you don’t want to do so, just press “cancel” button, then an answer would not have a picture. But if you do select a picture, that picture would be uploaded and serve as the attachment of that answer. After that you would have successfully posted one answer for the hit posted in the beginning,then the program would continue to ask you for more answers until you press the “cancel“ button.

#### HIT_TYPE_TEXT
You need to set `hit_type = HIT_TYPE_TEXT`, which would allow to post a brief-answered HIT. 
There is only one parameter to be set:

+ **photo_directory**: the the default directory of the pictures, which would be used when you want to post an answer with an image. Please make sure that your pictures have extended names, like “menu.png”, otherwise an error would show up for failing to find a picture with name like “menu”.

After this type of hit has been posted, the console would ask you to input choices for that question. To select multiple choices , just input the number ahead of those choices, ended with an **enter**. For exmaple, `1 3` + `enter` for selecting the first and third choice. If you want to stop posting answers, just pressing `enter` with nothing ahead.

##Contact##
zhenglibinmax@gmail.com
