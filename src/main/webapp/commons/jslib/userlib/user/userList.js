Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging', 
    'Ext.data.*' 
]); 
var baseUrl = null;
var pagingToolbarId = null;
var userListGridPanel = null;
Ext.onReady(function(){
	baseUrl = Ext.fly("basePathUrl").getValue(false);
	pagingToolbarId = "userListPagingToolbarId";
	userListGridPanel = 'userListGridPanel';
	var userListQueryField = 'userListQueryField';
	var userListStore = 'userListStore';
	Ext.define('User', {
	    extend: 'Ext.data.Model',
	    fields: ['userId','userName', 'password','sex','age','birthday','email','address','commentContext']
	});

	var store = Ext.create('Ext.data.Store', {
	    storeId:userListStore,
	    //分页大小 
        pageSize: 20, 
        model: 'User', 
        //是否在服务端排序 
        remoteSort: true,
	    proxy: {
	        //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可 
            type: 'ajax', 
            url: baseUrl+'/userAction.do?method=getUserListJsonData', 
             
            reader: { 
                root: 'root', 
                totalProperty:'count' 
            }, 
            simpleSortMode: true 
	    },
	    autoLoad: true 
	});
	
	var marginStr = '0 0 0 0';
	if(Ext.isIE){
		marginStr = '15 0 0 0';
	}
	var sexTypeTpl = new Ext.XTemplate(
		"<tpl if='Ext.isEmpty(sex)'>无</tpl>",
	    "<tpl if='sex == \"MAN\"'>男</tpl>",
	    "<tpl if='sex == \"WOMAN\"'>女</tpl>"
	);
	Ext.create("Ext.container.Viewport",{
		//title:'simpleStore',
		layout:'fit',
		border:0,
		items:{
			id:userListGridPanel,
			xtype:'grid',
		    frame:true,
		    store: store,
		    forceFit:true,
		    resizable:true,
		    border:0,
		    margin:marginStr,
		    resizable:false,
		    columns: [
		    	{ xtype: 'rownumberer'},
		        { header: '用户名',dataIndex: 'userName' },
		        { header: '密码',dataIndex: 'password'},
		        { header: '性别',dataIndex: 'sex',xtype: 'templatecolumn',tpl:sexTypeTpl},
		        { header: '年龄',dataIndex: 'age'},
		        { header: '生日',dataIndex: 'birthday'},
		        { header: '邮箱',dataIndex: 'email'},
		        { header: '地址',dataIndex: 'address'},
		        { header: '备注',dataIndex: 'commentContext'}
		    ],
		    bbar: Ext.create('Ext.PagingToolbar', { 
		    	id:pagingToolbarId,
	            store: store, 
	            displayInfo: true, 
	            displayMsg: '显示 {0} - {1} 条，共计 {2} 条', 
	            emptyMsg: "没有数据" 
	        }),
	        tbar:[{
	        	xtype: 'button', text: '新增用户',iconCls:'icon-user-add',handler:function(button,e){
	        		var frameUrl = window.parent.baseUrl+'/jsp/user/addUser.jsp';
	        		var listPage = Ext.getCmp(pagingToolbarId);
	        		window.parent.createWindow('addUserWin','新增用户',frameUrl,600,400,'icon-form-add','addUserSaveForm',listPage);
	        	}
	        },{
	        	xtype: 'button', text: '修改用户',iconCls:'icon-user-edit',handler:function(button,e){
	        		var selected = Ext.getCmp(userListGridPanel).getSelectionModel().getSelection();
	        		if(Ext.isEmpty(selected)){
	        			window.parent.Ext.Msg.alert("选择提醒","请选择一条记录");
	        		}else{
		        		var frameUrl = window.parent.baseUrl+'/jsp/user/addUser.jsp?userId='+selected[0].data.userId;
		        		var listPage = Ext.getCmp(pagingToolbarId);
		        		window.parent.createWindow('addUserWin','修改用户',frameUrl,600,400,'icon-form-edit','addUserSaveForm',listPage);
		        	}
	        	}
	        },{
	        	xtype: 'button', text: '删除用户',iconCls:'icon-user-delete',handler:function(button,e){
	        		var selected = Ext.getCmp(userListGridPanel).getSelectionModel().getSelection();
	        		if(Ext.isEmpty(selected)){
	        			window.parent.Ext.Msg.alert("选择提醒","请选择一条记录");
	        		}else{
		        		window.parent.Ext.Msg.show({
						    title: '删除确认',
						    msg: '您确认删除此记录吗？',
						    //width: 300,
						    buttons: Ext.Msg.YESNO,
						    buttonText:{'yes':'确定','no':'取消'},
						    //multiline: true,
						    fn: deleteConfirmHandler,
						    icon: Ext.window.MessageBox.QUESTION
						});
	        		}
	        	}
	        },{
	        	xtype: 'button', text: '查看用户',iconCls:'icon-user-view',handler:function(button,e){
	        		var selected = Ext.getCmp(userListGridPanel).getSelectionModel().getSelection();
	        		if(Ext.isEmpty(selected)){
	        			window.parent.Ext.Msg.alert("选择提醒","请选择一条记录");
	        		}else{
		        		var frameUrl = window.parent.baseUrl+'/jsp/user/addUser.jsp?userId='+selected[0].data.userId + '&viewFlag=true';
		        		window.parent.createWindow('addUserWin','查看用户',frameUrl,600,400,'icon-form-view',null,null);
		        	}
	        	}
	        },'->',{
	        	id:userListQueryField,
                xtype: 'textfield',
                width:200,
                emptyText:'请输入用户名称来模糊查询',
                //triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
                listeners: {
                 	specialkey: function(field, e){
                 		if (e.getKey() == e.ENTER) {
                 			var userNameValue = field.getValue();
                 			queryUserListGer(userListGridPanel,userNameValue);
                 		}
                 	}
                }
            },{xtype: 'button', text: '查询',iconCls:'icon-search',handler:function(button,e){
            	 var userNameValue = Ext.getCmp(userListQueryField).getValue();
            	 queryUserListGer(userListGridPanel,userNameValue);
             }
           }]
		},
		renderTo: Ext.getBody()
	});
	
//	var userListTrigger = Ext.getCmp(userListQueryTrigger);
//	var triggerWrap = userListTrigger.triggerWrap;
//	triggerWrap.on({
//		'click':{
//			fn:function(e,t,eOpts){
//				alert("22");
//			}
//		}
//	});
});

function deleteConfirmHandler(buttonId,text,opt){
	var selected = Ext.getCmp(userListGridPanel).getSelectionModel().getSelection();
	if(!Ext.isEmpty(selected)){
		if (buttonId == 'yes'){
			Ext.Ajax.request({
			    url: baseUrl+'/userAction.do?method=deleteUser',
			    params:{
			    	userId:selected[0].data.userId
			    },
			    success: function(response, opts) {
			        var obj = Ext.decode(response.responseText);
			        if(obj.success){
			        	 window.parent.Ext.Msg.alert("删除成功",obj.msg);
			        	 Ext.getCmp(pagingToolbarId).doRefresh();
			        }else{
			        	 window.parent.Ext.Msg.alert("删除失败",obj.msg);
			        }
			    },
			    failure: function(response, opts) {
			        window.parent.Ext.Msg.alert("提示","请刷新页面重试");
			    }
			});
	    }
	}
}

function queryUserListGer(userListGridPanel,userNameValue){
	 var gridStore = Ext.getCmp(userListGridPanel).getStore();
	 var gridProxy = gridStore.getProxy();
	 gridProxy.setExtraParam("userName",userNameValue);
	 gridStore.setProxy(gridProxy);
	 gridStore.load();
}