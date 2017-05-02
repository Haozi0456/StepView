# StepView
步骤指示器,流程指示器
 
  此流程指示器配置灵活,垂直流程指示器会根据文字的高度自动绘制指示图标的位置. 此功能是在badoualy/stepper-indicator的工程上优化修改而成.
  
  
![image](https://github.com/Haozi0456/StepView/blob/master/pre.png)
 
1. 水平流程指示器

HorizontalStepView stepHSView;

List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单",1);
        StepBean stepBean1 = new StepBean("打包",1);
        StepBean stepBean2 = new StepBean("出发",1);
        StepBean stepBean3 = new StepBean("送单",0);
        StepBean stepBean4 = new StepBean("完成",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);


        stepHSView
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context, R.color.yellow_500))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context, R.color.grey_700))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(context, R.color.green_500))//设置StepsView text完成字的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(context, R.color.red_400))//设置StepsView text未完成字的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context, R.drawable.checkbox_on_complet))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                
                
2.垂直步骤指示器

  VerticalStepView stepVSView;

 List<StepBean> list0 = new ArrayList<>();
        list0.add(new StepBean("接已提交定案，等待系统确认",1));
        list0.add(new StepBean("您的商品需要从外地调拨，我们会尽快处理，请耐心等待",1));
        list0.add(new StepBean("您的订单已经进入亚洲第一仓储中心1号库准备出库",1));
        list0.add(new StepBean("您的订单预计6月23日送达您的手中，618期间促销火爆，可能影响送货时间，请您谅解，我们会第一时间送到您的手中",1));
        list0.add(new StepBean("您的订单已打印完毕",1));
        list0.add(new StepBean("您的订单已拣货完成",1));
        list0.add(new StepBean("打包成功",1));
        list0.add(new StepBean("配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，参加评价还能赢取好多礼物哦",0));
        list0.add(new StepBean("感谢你在京东购物，欢迎你下次光临！",-1));
        stepVSView.reverseDraw(false)//default is true
                .setStepViewTexts(list0)//总步骤
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context, R.color.red_500))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context, R.color.grey_400))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(context, R.color.green_400))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(context, R.color.red_400))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context, R.drawable.checkbox_on_complet))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
