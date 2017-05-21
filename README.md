# Note_pad

# 一、说明：

## 1、Notpad简介：

小型笔记本app，用户能够根据分类来添加笔记。有“生活类”、“备忘录类”、“日常类”还有“其他类”。
点击类别，能在页面显示出该类别下的所有笔记，也可已经向已有笔记进行修改和删除。
可以对所有笔记进行标题的搜索查询。并且可以对app的界面背景进行更换，可以拍照设置也可以从系统图库设置。

## 2、功能：

  (1)、基本功能：时间戳，标题查询

  (2)、附加功能：更换编辑笔记背景颜色、界面优化、笔记分类、app背景图更换。


# 二、详细展示

## 打开app

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/main.png"
width="30%" height="30%" />

Pic 1.主界面

</center>

## 点击笔记，进入添加笔记界面

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/Notes.png"
width="30%" height="30%" />

Pic 2.笔记本

</center>

 

## 点击想要添加笔记的分类

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/addNotes.png"
width="30%" height="30%" />

Pic 3.日常类所有笔记

</center>

 

## 点击添加日常类笔记


 

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/editNewNote.png" width="30%" height="30%" />

Pic 4.1.编辑新笔记内容

</center>

<center>

<img src="https://github.com/LinBing43/AndroidAssignment/blob/master/Addsuccess.png" width="30%" height="30%" />

Pic 4.2 新笔记添加成功

</center>

## 可对已存在的笔记进行修改和删除

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/editOldN.png"
width="30%" height="30%" />

Pic 5.1 编辑过去笔记

</center>


<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/notechangeSuccess.png"
width="30%" height="30%" />

Pic 5.2 笔记修改成功

</center>

 

## 其他分类实现同样功能，操作相同

Tip:每个分类只显示该分类下的笔记。

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/showthiscategorydetail.png"
width="30%" height="30%" />

Pic 6.备忘录分类

</center>

 

## 笔记查询

Tip：默认显示所有笔记（无论分类）

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/SearchDefault.png" width="30%" height="30%" />

Pic 7.1 笔记查询

</center>

 

## 笔记查询2

输入“默默”，出现查询结果。(模糊)

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/fuzzyS.png" width="30%" height="30%" />

Pic 7.2 笔记查询结果

</center>

 

## 修改文本编辑背景颜色

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/BGCchoose.png" width="30%" height="30%" />

Pic 8.1 选择颜色

</center>

 

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/changedcolorpage.png" width="30%" height="30%" />

Pic 8.2 颜色修改成功

</center>

 

## 编辑背景图

点击修改背景

 

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/main.png" width="30%" height="30%" />

Pic 9.1 背景修改成功

</center>

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/choosePic.png" width="30%" height="30%" />

Pic 9.2 选中图片

</center>

<center>

<img src="https://github.com/LinBing43/Note_pad/blob/master/screenshots/changedbackgroundPic.png" width="30%" height="30%" />

Pic 9.3 背景修改成功

</center>


# 三、主要功能实现代码：

 

## 点击添加笔记，编辑新笔记

```

//保存笔记

 
public void saveNote() {

  //取得输入的内容 

   
String title = editText_add_title.getText().toString().trim();

   
String content = noteEditText_add_content.getText().toString().trim();

   
String time = editText_add_time.getText().toString().trim(); 

  //内容和标题都不能为空

   
if ("".equals(title) || "".equals(content)) {

     
Toast.makeText(AddActivity.this, "名称和内容都不能为空", Toast.LENGTH_SHORT).show();

  }
else {

    DBUserNote
dbUserNote = new DBUserNote();

    dbUserNote.setCreatTimeAsId(getTime());

    dbUserNote.setnoteType(title);

    dbUserNote.setSign(content);

    dbUserNote.setName(getIntent().getStringExtra("name"));

    DBUserNoteUtils.getInstance().insertOneData(dbUserNote);

   
RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));

   
Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

   
AddActivity.this.finish();

    }

  }

  

  //返回当前的时间

 
public String formatTime() {

   
Date d = new Date();

   
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
HH:mm:ss");

   
String time = sdf.format(d);

   
return time;

  }

 
public long getTime() {    return
System.currentTimeMillis();//获取系统时间戳

  }

```


## 显示用户所选该类型下的笔记（以生活类为例）
 

tip:其他类别笔记显示代码相同

```

//获取生活类笔记的Listview

public void getListViewData(){
   
resultDaoList = DBUserNoteUtils.getInstance().queryDataDependMedia("生活");

if (resultDaoList!=null&&resultDaoList.size()>0){
lvDBDailyLifeActivity.setVisibility(View.VISIBLE);     
myAdapter = new MyKindAdapter(DBDailyLifeActivity.this);
myAdapter.setLists(resultDaoList);       
lvDBDailyLifeActivity.setAdapter(myAdapter);

}else {
lvDBDailyLifeActivity.setVisibility(View.GONE);
    }

}


```

## 点击已存在的笔记进行修改或删除

```

//点击已存在的笔记进行修改或删除，转到笔记编辑页面NotXiangQingActivity

 
public void setListViewClick()

  {

   
lvDBRichangActivity.setOnItemClickListener(new
AdapterView.OnItemClickListener() {

     
@Override

     
public void onItemClick(AdapterView<?> parent, View view, int
position, long id) {

       
Intent intent = new
Intent(DBRiChangActivity.this,NotXiangQingActivity.class);    

       
intent.putExtra("NotID", 
resultDaoList.get(position).getCreatTimeAsId());

       
intent.putExtra("name", "日常");   

       
startActivity(intent);

       
}

    
});

   }

```

## 笔记再次编辑主要函数： 

```

public void saveNote() {

   
//取得输入的内容

   
String title = edtNotXiangQingTitle.getText().toString().trim();

   
String content = edtNotXiangQingCount.getText().toString().trim();

   
//判断：内容和标题都不能为空

   
if ("".equals(title) || "".equals(content)) {

       
Toast.makeText(NotXiangQingActivity.this, "名称和内容都不能为空",
Toast.LENGTH_SHORT).show();

    }
else {

       
DBUserNote dbUserNote = new DBUserNote();

       
dbUserNote.setCreatTimeAsId(getIntent().getLongExtra("NotID",
0));

       
dbUserNote.setnoteType(title);

       
dbUserNote.setSign(content);

       
dbUserNote.setName(getIntent().getStringExtra("name"));    

       
//更新笔记至数据库

       
DBUserNoteUtils.getInstance().updateData(dbUserNote);

       
Toast.makeText(NotXiangQingActivity.this, "修改成功",
Toast.LENGTH_SHORT).show();

       
RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));

    }

}

```
 
## 文本编辑界面设置背景颜色

```
private void ChooseBackgroundColor(){
  builder = new AlertDialog.Builder(AddActivity.this);
  LayoutInflater inflater=getLayoutInflater();
  View view=inflater.inflate(R.layout.dialogcolor,null);
  final LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.dialoglinear);
  final int[] colorArrayList={
    Color.parseColor("#FFFFFF"),//白
    Color.parseColor("#FFF8DC"),//米色  
    Color.parseColor("#fcf9a4"),//黄   
    Color.parseColor("#abed65"),//绿   
    Color.parseColor("#E0FFFF"),//天蓝  
    Color.parseColor("#1cdaef"),//蓝绿  
    Color.parseColor("#fa77ab"),//粉色
    };
  for (int i=0;i<7;i++){
    ImageView imageView=new ImageView(AddActivity.this);
    imageView.setLayoutParams(new LinearLayout.LayoutParams(100,120));
    imageView.setBackgroundColor(colorArrayList[i]);    
    final int Color = i; 
    imageView.setOnClickListener(new View.OnClickListener() { 
    @Override        
    public void onClick(View v) { 
      layout.setBackgroundColor(colorArrayList[Color]);
      } 
    });
    linearLayout.addView(imageView);
  } 
  builder.setView(view).create().show();
}

```

## 数据库主要功能代码：

### 1、数据设置：

```
public class DBUserNote {
    @Id(autoincrement = false)
    public long creatTimeAsId;  //把创建时间作为表的ID
    @Property(nameInDb = "DBUserNote")
    public String name;  // 每个笔记名称
    public String noteType;//笔记类别
    public String sign;  // 笔记内容
    @Generated(hash = 1349221561)
    public DBUserNote(long creatTimeAsId, String name, String noteType,
            String sign) {
        this.creatTimeAsId = creatTimeAsId;
        this.name = name;
        this.noteType = noteType;
        this.sign = sign;
    }


```

### 2、数据基本操作 （增删改查）

```
public class DBUserNoteUtils {

    private DBUserNoteDao dbUserNoteDao ;
    private static DBUserNoteUtils dbUserNoteUtils=null;

    public DBUserNoteUtils  (Context context){
        dbUserNoteDao= DaoManager.getInstance(context).getNewSession().getDBUserNoteDao();
    }

    public static DBUserNoteUtils getInstance(){
        return dbUserNoteUtils;
    }
    public static void Init(Context context){
        if(dbUserNoteUtils == null){
            dbUserNoteUtils=new  DBUserNoteUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     * @param dbUserNote
     * @return
     */
    public void insertOneData(DBUserNote dbUserNote){
        dbUserNoteDao.insertOrReplace(dbUserNote);
    }

      /**
     * 完成对数据库中删除一条数据操作
     * @param dbUserNote
     * @return
     */
    public boolean deleteOneData(DBUserNote dbUserNote){
        boolean flag = false;
        try{
            dbUserNoteDao.delete(dbUserNote);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     * @return
     */
    public boolean deleteOneDataByKey(long id){
        boolean flag = false;
        try{
            dbUserNoteDao.deleteByKey(id);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

       /**
     * 完成对数据库更新数据操作
     * @return
     */
    public boolean updateData(DBUserNote dbUserNote){
        boolean flag = false;
        try{
            dbUserNoteDao.update(dbUserNote);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     * @return
     */
    public boolean updateManData(List<DBUserNote> dbUserNote){
        boolean flag = false;
        try{
            dbUserNoteDao.updateInTx(dbUserNote);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     * @return
     */
    public DBUserNote queryData(long id) {
        return dbUserNoteDao.load(id);
    }

    /**
     * 返回对数据库查询所有笔记操作
     * @return
     */
    public List<DBUserNote> queryData() {
        return dbUserNoteDao.loadAll();
    }

    /**
     *完成对数据库条件查询数据操作     * @return
     */
    public List<DBUserNote> queryDataDependMedia(String name) {
        return dbUserNoteDao.queryBuilder().where(DBUserNoteDao.Properties.Name.eq(name)).build().list();
    }

    /**
     * 完成对数据库按笔记标题查询数据操作
     * @return
     */
    public List<DBUserNote> queryDataDependNotTitle(String noteType) {
        return dbUserNoteDao.queryBuilder().where(DBUserNoteDao.Properties.NoteType.like("%"+noteType+"%")).build().list();
    }
}

 


```

