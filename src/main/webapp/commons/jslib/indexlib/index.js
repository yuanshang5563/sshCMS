var baseUrl = null;
Ext.onReady(function(){
	baseUrl = Ext.fly("basePathUrl").getValue(false);
	Ext.create('Ext.container.Viewport', {
		id:'mainViewPortId',
	    layout: 'border',
	    items: [{
	    	id:'northId',
	        region: 'north',
	        html: '<h1 class="x-panel-header">人员管理系统</h1>',
	        border: false,
	        margins: '0 0 5 0',
	        border:0,
	        frame:true
	    }, {
	    	id:'westId',
	        region: 'west',
	        collapsible: true,
	        //title: 'Navigation',
	        width: 150,
	        border:0,
        	layout: {
		        type: 'accordion',
		        titleCollapse: true,
		        animate: true,
		        activeOnTop: true
			}
//	        items:[{
//				    	xtype:'panel',
//				        title: 'Panel 1',
//				        html: 'Panel content!',
//				        collapsible:true
//				    },{
//				        title: 'Panel 2',
//				        html: 'Panel content!'
//				    },{
//				        title: 'Panel 3',
//				        html: 'Panel content!'
//				   }]
	    	}, {
	    	id:'southId',
	        region: 'south',
	        //title: 'South Panel',
	        collapsible: true,
	        html: 'Information goes here',
	        split: true,
	        height: 100,
	        minHeight: 100,
	        border:0,
	        frame:true
	    }, {
	    	id:'centerId',
	        region: 'center',
	        //title:'center',
	        xtype: 'tabpanel', // TabPanel本身没有title属性
	        //activeTab: 0,      // 配置默认显示的激活面板
	        //layout:'fit',
	        //width:700,
	        frame:true,
	        border:0,
	        margin:'0 0 0 0'
//	        items: [{
//	        	xtype:'panel',
//	            title: 'portal Tab',
//	            layout:'fit',
//	            border:0,
//	            floating:false,
//	            margin:'0 0 0 0',
//	            closable:true,
//	            html: '<iframe src="'+baseUrl+'/jsp/system/portal/portal.jsp'+'" width="100%" height="100%"></iframe>'
//	        },{
//	        	xtype:'panel',
//	            title: 'Default Tab',
//	            layout:'fit',
//	            border:0,
//	            floating:false,
//	            margin:'0 0 0 0',
//	             closable:true,
//	            html: '<iframe src="'+baseUrl+'/jsp/user/userList.jsp'+'" width="100%" height="100%"></iframe>'
//	        }]
	    }]
	});
	
	var allSubMenus = Ext.fly("allSubMenus").getValue(false);
	//console.log("--- " + allSubMenus);
	var allSubMenuJsons = Ext.JSON.decode(allSubMenus);
	var westCom = Ext.ComponentManager.get("westId");
	var centerPanel = Ext.ComponentManager.get("centerId");
//	Ext.Array.each(allSubMenuJsons, function(name, index, subMenu) {
//		westCom.add(Ext.create('Ext.panel.Panel', {title:subMenu[0].menuName}));
//	    console.log("--- " +name);
//	    console.log(index);
//	    console.log(subMenu);
//	});
	
	var menuItems = new Array();
	for(var i = 0; i <allSubMenuJsons.length;i++ ){
		if(allSubMenuJsons[i].menuType=="TREENODE"){
			westCom.add(Ext.create('Ext.panel.Panel', {id:allSubMenuJsons[i].menuId,title:allSubMenuJsons[i].menuName,iconCls:allSubMenuJsons[i].icon}));
		}else{
			menuItems.push(allSubMenuJsons[i]);
		}
	}
	for(var j = 0; j <menuItems.length;j++ ){
		//console.log(menuItems[j]);
		var subMenu = Ext.ComponentManager.get(menuItems[j].parentMenu.menuId);
		console.log("--- " +subMenu);//1231231
		subMenu.add(Ext.create('Ext.menu.Menu', {id:menuItems[j].menuId,plain: false,floating: false,items:[{id:menuItems[j].menuUrl,text:menuItems[j].menuName,iconCls:menuItems[j].icon,
				listeners:{
					click:{
						fn:function(item,e,eOpts){
							if(!Ext.ComponentManager.get(item.id+'tab')){
								addCenterTabClick(centerPanel,baseUrl,item,e,eOpts);
								Ext.ComponentManager.get(item.id+'tab').show();
							}else{
								Ext.ComponentManager.get(item.id+'tab').show();
							}
						}
					}
				}
			}]})
		);
		//var menuItem = Ext.ComponentManager.get(menuItems[j].menuUrl);
		//menuItem.on({click:{fn: 'addCenterTabClick', scope: this, single: false}});
	}
});

function addCenterTabClick(centerPanel,baseUrl,item,e,eOpts){
		centerPanel.add(Ext.create('Ext.tab.Panel', {
		id:item.id+'tab',
		title:item.text,
		layout:'fit',
		border:0,
		//autoShow:true,
		//floating:false,
		margin:'0 0 0 0',
		padding:'0 0 0 0',
		closable:true,
		//frameHeader:false,
		//header:false,
		html: '<iframe id="'+item.id+'iframe'+'" name="'+item.id+'iframe'+'" src="'+baseUrl+"/"+item.id+'" width="100%" height="100%"></iframe>'					
	}));
}

function createWindow(id,title,frameUrl,width,height,iconCls,formId,listPage){
	var closeBtn = Ext.create('Ext.Button',{ 
		    	text:'关闭',iconCls:'icon-cancel',iconAlign:'right',handler:function(button,e){
  					Ext.getCmp(id).close();
  				} 
  			});
  	var btns = new Array();
  	if(!Ext.isEmpty(formId)&&!Ext.isEmpty(listPage)){
		var submitBtn = Ext.create('Ext.Button',{ 
		    	text:'保存',iconCls:'icon-save',iconAlign:'right',handler:function(button,e){
		    		var iframeId = id+'iframe';
		    		var iframe = window.frames[iframeId];
		    		var saveForm = iframe.Ext.getCmp(formId);
		    		if(saveForm.isValid()){
				    	saveForm.submit({
		                    success: function(form, action) {
		                       var flag = action.result.success;
		                       if(action.result.success){
		                       	Ext.Msg.alert('成功', action.result.msg);
		                       	listPage.doRefresh();
		                       }else{
		                       	Ext.Msg.alert('异常', action.result.msg);		
		                       }
		                       Ext.getCmp(id).close();
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('失败','请刷新页面重试！');
		                        Ext.getCmp(id).close();
		                    }
		                });
		    		}//else{
		    		//	Ext.Msg.alert('Failed','请输入必填项！');
		    		//	saveForm.checkValidity();
		    		//}
				} 
			});
		btns.push(submitBtn);
  	}
  	btns.push(closeBtn);
	Ext.create('Ext.window.Window', {
			id:id,
		    title: title,
		    height: height,
		    width: width,
		    layout: 'fit',
		    iconCls:iconCls,
		    html: '<iframe id="'+id+'iframe'+'" name="'+id+'iframe'+'" src="'+frameUrl+'" width="100%" height="100%"></iframe>',
		    buttons: btns
		}).show();
}