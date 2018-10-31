var baseUrl = null;
var pagingToolbarId = null;
var menuListGridPanel = null;
Ext.onReady(function(){
	baseUrl = Ext.fly("basePathUrl").getValue(false);
	pagingToolbarId = "menuListPagingToolbarId";
	menuListGridPanel = 'menuListGridPanel';
	var menuListQueryField = 'menuListQueryField';
	Ext.define('Menu', {
	    extend: 'Ext.data.Model',
	    fields: ['menuId','menuName', 'menuUrl','menuType','parentMenu','icon','orderNum']
	});

	var store = Ext.create('Ext.data.Store', {
	    storeId:'menuListStore',
	    //分页大小 
        pageSize: 20, 
        model: 'Menu', 
        //是否在服务端排序 
        remoteSort: true,
	    proxy: {
	        //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可 
            type: 'ajax', 
            url: baseUrl+'system/menuAction.do?method=getMenuListJsonData', 
             
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
	var menuTypeTpl = new Ext.XTemplate(
		"<tpl if='Ext.isEmpty(menuType)'>无</tpl>",
	    "<tpl if='menuType == \"TREENODE\"'>节点</tpl>",
	    "<tpl if='menuType == \"TREELEAF\"'>叶子</tpl>"
	);
	var parentMenuTpl = new Ext.XTemplate(
	    "<tpl if='Ext.isEmpty(parentMenu)'>无</tpl>",
	    "<tpl if='!Ext.isEmpty(parentMenu)'>{parentMenu.menuName}</tpl>"
	);
	Ext.create("Ext.container.Viewport",{
		//title:'simpleStore',
		layout:'fit',
		border:0,
		items:{
			id:menuListGridPanel,
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
		        { header: '菜单名称',dataIndex: 'menuName' },
		        { header: '菜单路径',dataIndex: 'menuUrl'},
		        { header: '菜单类型',dataIndex: 'menuType',xtype: 'templatecolumn',tpl:menuTypeTpl},
		        { header: '父菜单',dataIndex: 'parentMenu',xtype: 'templatecolumn',tpl:parentMenuTpl},
		        { header: '菜单图标',dataIndex: 'icon'},
		        { header: '排序号',dataIndex: 'orderNum'}
		    ],
		    bbar: Ext.create('Ext.PagingToolbar', { 
		    	id:pagingToolbarId,
	            store: store, 
	            displayInfo: true, 
	            displayMsg: '显示 {0} - {1} 条，共计 {2} 条', 
	            emptyMsg: "没有数据" 
	        }),
	        tbar:[{
	        	xtype: 'button', text: '新增菜单',iconCls:'icon-add',handler:function(button,e){
	        		var frameUrl = window.parent.baseUrl+'jsp/system/menu/menuForm.jsp';
	        		var listPage = Ext.getCmp(pagingToolbarId);
	        		window.parent.createWindow('addMenuWin','新增菜单',frameUrl,600,400,'icon-form-add','menuSaveForm',listPage);
	        	}
	        },{
	        	xtype: 'button', text: '修改菜单',iconCls:'icon-edit',handler:function(button,e){
	        		var selected = Ext.getCmp(menuListGridPanel).getSelectionModel().getSelection();
	        		if(Ext.isEmpty(selected)){
	        			window.parent.Ext.Msg.alert("选择提醒","请选择一条记录");
	        		}else{
		        		var frameUrl = window.parent.baseUrl+'jsp/system/menu/menuForm.jsp?menuId='+selected[0].data.menuId;
		        		var listPage = Ext.getCmp(pagingToolbarId);
		        		window.parent.createWindow('editMenuWin','修改菜单',frameUrl,600,400,'icon-form-edit','menuSaveForm',listPage);
		        	}
	        	}
	        },{
	        	xtype: 'button', text: '删除菜单',iconCls:'icon-delete',handler:function(button,e){
	        		var selected = Ext.getCmp(menuListGridPanel).getSelectionModel().getSelection();
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
	        	xtype: 'button', text: '查看菜单',iconCls:'icon-view',handler:function(button,e){
	        		var selected = Ext.getCmp(menuListGridPanel).getSelectionModel().getSelection();
	        		if(Ext.isEmpty(selected)){
	        			window.parent.Ext.Msg.alert("选择提醒","请选择一条记录");
	        		}else{
		        		var frameUrl = window.parent.baseUrl+'jsp/system/menu/menuForm.jsp?menuId='+selected[0].data.menuId + '&viewFlag=true';
		        		window.parent.createWindow('addUserWin','查看菜单',frameUrl,600,400,'icon-form-view',null,null);
		        	}
	        	}
	        },'->',{
	        	id:menuListQueryField,
                xtype: 'textfield',
                width:200,
                emptyText:'请输入菜单名称来模糊查询',
                listeners: {
                 	specialkey: function(field, e){
                 		if (e.getKey() == e.ENTER) {
                 			var menuNameValue = field.getValue();
                 			queryMenuListGer(menuNameValue);
                 		}
                 	}
                }
            },{xtype: 'button', text: '查询',iconCls:'icon-search',handler:function(button,e){
            	 var menuNameValue = Ext.getCmp(menuListQueryField).getValue();
            	 queryMenuListGer(menuNameValue);
             }
           }]
		},
		renderTo: Ext.getBody()
	});

});

function deleteConfirmHandler(buttonId,text,opt){
	var selected = Ext.getCmp(menuListGridPanel).getSelectionModel().getSelection();
	if(!Ext.isEmpty(selected)){
		if (buttonId == 'yes'){
			Ext.Ajax.request({
			    url: baseUrl+'system/menuAction.do?method=deleteMenu',
			    params:{
			    	menuId:selected[0].data.menuId
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


function queryMenuListGer(menuNameValue){
	 var gridStore = Ext.getCmp(menuListGridPanel).getStore();
	 var gridProxy = gridStore.getProxy();
	 gridProxy.setExtraParam("menuName",menuNameValue);
	 gridStore.setProxy(gridProxy);
	 gridStore.load();
}